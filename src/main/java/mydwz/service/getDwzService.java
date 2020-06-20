package mydwz.service;

import mydwz.beans.MyDwz;

public interface getDwzService {

    MyDwz getMyDwz(String longUrl);

    String getMyOriginalWz(String shortUrl);
}
