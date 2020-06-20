package mydwz.dao;

import mydwz.beans.MyDwz;
import org.apache.ibatis.annotations.Param;

public interface getUrl {

    MyDwz getUrl(@Param("shortUrl") String shortUrl);
}
