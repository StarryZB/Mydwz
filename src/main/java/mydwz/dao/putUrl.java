package mydwz.dao;

import org.apache.ibatis.annotations.Param;

public interface putUrl {

    void putUrl(@Param("shortUrl") String shortUrl, @Param("longUrl") String longUrl);

}
