package mydwz.utils;

import org.springframework.stereotype.Component;

import java.util.BitSet;

@Component
public class bloomFilter {

    private static final int[] HashSeeds=new int[]{3,5,7,11,13,17,19};

    /**
     * Hash工具类的数组
     */
    private static BloomHash[] HashList=new BloomHash[HashSeeds.length];

    /**
     * BloomFilter的长度，最好为插入数量的10倍，目前为2的20次方，大约100万个
     */
    private static final int BloomLength=1<<20;

    /**
     * 对位的操作类，java自带的BitSet，共BloomLength个bit
     */
    private BitSet bitSet=new BitSet(BloomLength);


    public bloomFilter(){
        //初始化Hash工具类的数组,每个hash工具类的hash函数都不同
        for(int i=0;i<HashSeeds.length;i++){
            HashList[i]=new BloomHash(BloomLength, HashSeeds[i]);
        }
    }

    /**在布隆过滤器中加入值value，在多个hash函数生成的hashcode对应的位置上，置1
     * @param value 字符串，如果为数字，可以自己转化成string
     */
    public void addValue(String value){
        for(int i=0;i<HashSeeds.length;i++){
            //根据对应的hash函数得到hashcode
            int hashcode=HashList[i].hashCode(value);
            //在位图中，将对应的位，设置为1
            bitSet.set(hashcode);
        }
    }

    /**在布隆过滤器中,检验是否可能有值value
     * @param value
     * @return 如果返回false，则一定没有<br> 如果返回true，就代表有可能有
     */
    public boolean existsValue(String value){
        boolean result=true;
        for(int i=0;i<HashSeeds.length;i++){
            //根据对应的hash函数得到hashcode
            int hashcode=HashList[i].hashCode(value);
            //将result与对应位置上的0或1 做与运算
            //如果全为1，则result最后为1
            //如果有一个位置上为0，则最后result为0
            result=result&bitSet.get(hashcode);
        }
        return result;
    }

    static class BloomHash {

        /**
         * Hash工具类返回的hashcode的最大长度<br>
         * maxLength为2的n次方，返回的hashcode为[0,2^n-1]
         */
        public int maxLength;

        /**
         * Hash函数生成哈希码的关键字
         */
        public int seed;

        public BloomHash(int maxLength, int seed) {
            this.maxLength = maxLength;
            this.seed = seed;
        }

        /**返回字符串string的hashcode，大小为[0,maxLength-1]
         * @param string
         * @return
         */
        public int hashCode(String string){
            int result=0;
            //这个构建hashcode的方式类似于java的string的hashcode方法
            //只是我这里是可以设置的seed，它那里是31
            for(int i=0;i<string.length();i++){
                result=result+seed*string.charAt(i);
            }
            //maxLength-1=111111,相当于result mod (maxLength-1)
            //保证结果在[0,maxLength-1]
            return (maxLength-1)&result;
        }

//        public static void main(String[] args) {
//            bloomFilter bloomFilter = new bloomFilter();
//            bloomFilter.addValue("28GrFMtjkA");
//            System.out.println(bloomFilter.existsValue("28GrFMtjkA"));
//        }
    }
}
