package mydwz.service.impl;

import mydwz.beans.MyDwz;
import mydwz.dao.Redis.RedisDao;
import mydwz.dao.getUrl;
import mydwz.dao.putUrl;
import mydwz.service.getDwzService;
import mydwz.utils.bloomFilter;
import mydwz.utils.getSnowFlakeId;
import mydwz.utils.to62RadixString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class getDwzServiceImpl implements getDwzService {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private bloomFilter bloomFilter;

    @Autowired
    private putUrl putUrl;

    @Autowired
    private getUrl getUrl;

    @Override
    public MyDwz getMyDwz(String longUrl) {
        /*1、Redis缓存查询，若存在，返回
        * 2、利用雪花算法发号，转换短网址，存入（长-短/短-长）Redis、（短）bloom，(短-长）存MySQL，返回*/
        MyDwz myDwz = new MyDwz();
        myDwz.setLongUrl(longUrl);
        String shortUrl = redisDao.getDwz(longUrl);
        if (shortUrl != null) {
            myDwz.setShortUrl(shortUrl);
            return myDwz;
        }
        getSnowFlakeId worker = new getSnowFlakeId(1,1,1);
        long snowFlakeId = worker.nextId();
        shortUrl = to62RadixString.to62RadixString(snowFlakeId);
        myDwz.setShortUrl(shortUrl);
        redisDao.setDwz(longUrl, shortUrl);
        redisDao.setDwz(shortUrl,longUrl);
        bloomFilter.addValue(shortUrl);
        putUrl.putUrl(shortUrl, longUrl);
        return myDwz;
    }

    @Override
    public String getMyOriginalWz(String shortUrl) {
        /*1、在bloom里面查找，若没有，则返回null
        * 2、从Redis查找，若有，返回
        * 3、从MySql查找，返回*/
        boolean isPassBloomFilter = bloomFilter.existsValue(shortUrl);
        if (!isPassBloomFilter) {
            return null;
        }
        String longUrl = redisDao.getCwz(shortUrl);
        if (longUrl != null) {
            return longUrl;
        }
        MyDwz myDwz = getUrl.getUrl(shortUrl);
        return myDwz.getLongUrl();
    }
}
