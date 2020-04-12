package com.huanxi.music;

import com.alibaba.fastjson.JSONObject;
import com.huanxi.music.http.downloader.IDownloader;
import com.huanxi.music.http.downloader.SingleThreadDownloader;
import com.huanxi.music.music.kuwo.KuwoService;
import com.huanxi.music.music.kuwo.MusicPiP;
import com.huanxi.music.music.kuwo.Searcher;
import com.huanxi.music.music.kuwo.vo.MusicInfo;
import com.huanxi.music.music.kuwo.vo.ReturnMessage;
import com.huanxi.music.nosql.CacheImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.Semaphore;

@SpringBootTest
class MusicApplicationTests {

    @Resource
    Searcher searcher;


    @Resource
    private ThreadPoolTaskExecutor executor;
    @Resource
    private KuwoService kuwoService;
    @Resource
    MusicPiP musicPiP;


    @Resource
    CacheImpl cache;


    @Test
    void testMo() {
        kuwoService.getSearchKey("后来遇a");
    }

    @Test
    void testC() {
        String s = "{\"code\":200,\"curTime\":1586588502626,\"data\":{\"total\":\"2809\",\"list\":[{\"musicrid\":\"MUSIC_7149583\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img2.kuwo.cn/star/albumcover/300/64/39/3540704654.jpg\",\"isstar\":0,\"rid\":7149583,\"duration\":215,\"content_type\":\"0\",\"track\":8,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2016-06-24\",\"album\":\"周杰伦的床边故事\",\"albumid\":555949,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img2.kuwo.cn/star/albumcover/500/64/39/3540704654.jpg\",\"songTimeMinutes\":\"03:35\",\"isListenFee\":true,\"pic120\":\"http://img2.kuwo.cn/star/albumcover/120/64/39/3540704654.jpg\",\"name\":\"告白气球\",\"online\":1,\"payInfo\":{\"play\":\"1100\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_228908\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img2.kuwo.cn/star/albumcover/300/64/96/2266534336.jpg\",\"isstar\":0,\"rid\":228908,\"duration\":269,\"content_type\":\"0\",\"track\":3,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2003-07-31\",\"album\":\"叶惠美\",\"albumid\":1293,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img2.kuwo.cn/star/albumcover/500/64/96/2266534336.jpg\",\"songTimeMinutes\":\"04:29\",\"isListenFee\":true,\"pic120\":\"http://img2.kuwo.cn/star/albumcover/120/64/96/2266534336.jpg\",\"name\":\"晴天\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_324244\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img2.kuwo.cn/star/albumcover/300/36/9/13515874.jpg\",\"isstar\":0,\"rid\":324244,\"duration\":237,\"content_type\":\"0\",\"track\":3,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2007-11-02\",\"album\":\"我很忙\",\"albumid\":12449,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img2.kuwo.cn/star/albumcover/500/36/9/13515874.jpg\",\"songTimeMinutes\":\"03:57\",\"isListenFee\":true,\"pic120\":\"http://img2.kuwo.cn/star/albumcover/120/36/9/13515874.jpg\",\"name\":\"青花瓷\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_76323299\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img1.kuwo.cn/star/albumcover/300/12/37/4156270827.jpg\",\"isstar\":0,\"rid\":76323299,\"duration\":222,\"content_type\":\"0\",\"track\":1,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2019-09-16\",\"album\":\"说好不哭 (with 五月天阿信)\",\"albumid\":10685968,\"pay\":\"255\",\"artistid\":336,\"albumpic\":\"http://img1.kuwo.cn/star/albumcover/500/12/37/4156270827.jpg\",\"songTimeMinutes\":\"03:42\",\"isListenFee\":true,\"pic120\":\"http://img1.kuwo.cn/star/albumcover/120/12/37/4156270827.jpg\",\"name\":\"说好不哭 (with 五月天阿信)\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"cannotOnlinePlay\":0,\"feeType\":{\"album\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_440613\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img3.kuwo.cn/star/albumcover/300/72/44/3648126291.jpg\",\"isstar\":0,\"rid\":440613,\"duration\":223,\"content_type\":\"0\",\"track\":11,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2008-10-15\",\"album\":\"魔杰座\",\"albumid\":29218,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img3.kuwo.cn/star/albumcover/500/72/44/3648126291.jpg\",\"songTimeMinutes\":\"03:43\",\"isListenFee\":true,\"pic120\":\"http://img3.kuwo.cn/star/albumcover/120/72/44/3648126291.jpg\",\"name\":\"稻香\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_94237\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img2.kuwo.cn/star/albumcover/300/30/97/4276557883.jpg\",\"isstar\":0,\"rid\":94237,\"duration\":298,\"content_type\":\"0\",\"track\":2,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2004-08-03\",\"album\":\"七里香\",\"albumid\":4533,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img2.kuwo.cn/star/albumcover/500/30/97/4276557883.jpg\",\"songTimeMinutes\":\"04:58\",\"isListenFee\":true,\"pic120\":\"http://img2.kuwo.cn/star/albumcover/120/30/97/4276557883.jpg\",\"name\":\"七里香\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_118980\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img3.kuwo.cn/star/albumcover/300/32/88/1567952196.jpg\",\"isstar\":0,\"rid\":118980,\"duration\":226,\"content_type\":\"0\",\"track\":1,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2005-11-01\",\"album\":\"十一月的萧邦\",\"albumid\":14466,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img3.kuwo.cn/star/albumcover/500/32/88/1567952196.jpg\",\"songTimeMinutes\":\"03:46\",\"isListenFee\":true,\"pic120\":\"http://img3.kuwo.cn/star/albumcover/120/32/88/1567952196.jpg\",\"name\":\"夜曲\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_156491\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img3.kuwo.cn/star/albumcover/300/73/62/2112638232.jpg\",\"isstar\":0,\"rid\":156491,\"duration\":297,\"content_type\":\"0\",\"track\":2,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2003-11-01\",\"album\":\"寻找周杰伦\",\"albumid\":1294,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img3.kuwo.cn/star/albumcover/500/73/62/2112638232.jpg\",\"songTimeMinutes\":\"04:57\",\"isListenFee\":true,\"pic120\":\"http://img3.kuwo.cn/star/albumcover/120/73/62/2112638232.jpg\",\"name\":\"断了的弦\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_138243\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img3.kuwo.cn/star/albumcover/300/45/96/3463817628.jpg\",\"isstar\":0,\"rid\":138243,\"duration\":264,\"content_type\":\"0\",\"track\":2,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2006-09-01\",\"album\":\"依然范特西\",\"albumid\":9034,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img3.kuwo.cn/star/albumcover/500/45/96/3463817628.jpg\",\"songTimeMinutes\":\"04:24\",\"isListenFee\":true,\"pic120\":\"http://img3.kuwo.cn/star/albumcover/120/45/96/3463817628.jpg\",\"name\":\"听妈妈的话\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_728668\",\"artist\":\"周杰伦&杨瑞代\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img2.kuwo.cn/star/albumcover/300/98/24/205164915.jpg\",\"isstar\":0,\"rid\":728668,\"duration\":254,\"content_type\":\"0\",\"track\":9,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2010-05-18\",\"album\":\"跨时代\",\"albumid\":49449,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img2.kuwo.cn/star/albumcover/500/98/24/205164915.jpg\",\"songTimeMinutes\":\"04:14\",\"isListenFee\":true,\"pic120\":\"http://img2.kuwo.cn/star/albumcover/120/98/24/205164915.jpg\",\"name\":\"爱的飞行日记\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_728677\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img2.kuwo.cn/star/albumcover/300/98/24/205164915.jpg\",\"isstar\":0,\"rid\":728677,\"duration\":262,\"content_type\":\"0\",\"track\":3,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2010-05-18\",\"album\":\"跨时代\",\"albumid\":49449,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img2.kuwo.cn/star/albumcover/500/98/24/205164915.jpg\",\"songTimeMinutes\":\"04:22\",\"isListenFee\":true,\"pic120\":\"http://img2.kuwo.cn/star/albumcover/120/98/24/205164915.jpg\",\"name\":\"烟花易冷\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_40079875\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img3.kuwo.cn/star/albumcover/300/86/93/2359259663.jpg\",\"isstar\":0,\"rid\":40079875,\"duration\":270,\"content_type\":\"0\",\"track\":1,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2018-01-18\",\"album\":\"等你下课\",\"albumid\":5323930,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img3.kuwo.cn/star/albumcover/500/86/93/2359259663.jpg\",\"songTimeMinutes\":\"04:30\",\"isListenFee\":true,\"pic120\":\"http://img3.kuwo.cn/star/albumcover/120/86/93/2359259663.jpg\",\"name\":\"等你下课(with 杨瑞代)\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_83728113\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img1.kuwo.cn/star/albumcover/300/49/30/1387478456.jpg\",\"isstar\":0,\"rid\":83728113,\"duration\":266,\"content_type\":\"0\",\"track\":1,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2019-12-15\",\"album\":\"我是如此相信\",\"albumid\":11749812,\"pay\":\"16515324\",\"artistid\":336,\"albumpic\":\"http://img1.kuwo.cn/star/albumcover/500/49/30/1387478456.jpg\",\"songTimeMinutes\":\"04:26\",\"isListenFee\":false,\"pic120\":\"http://img1.kuwo.cn/star/albumcover/120/49/30/1387478456.jpg\",\"name\":\"我是如此相信\",\"online\":1,\"payInfo\":{\"play\":\"1100\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_156522\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img4.kuwo.cn/star/albumcover/300/14/76/860786194.jpg\",\"isstar\":0,\"rid\":156522,\"duration\":270,\"content_type\":\"0\",\"track\":3,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2001-09-20\",\"album\":\"范特西\",\"albumid\":1287,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img4.kuwo.cn/star/albumcover/500/14/76/860786194.jpg\",\"songTimeMinutes\":\"04:30\",\"isListenFee\":true,\"pic120\":\"http://img4.kuwo.cn/star/albumcover/120/14/76/860786194.jpg\",\"name\":\"简单爱\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_118990\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img3.kuwo.cn/star/albumcover/300/32/88/1567952196.jpg\",\"isstar\":0,\"rid\":118990,\"duration\":300,\"content_type\":\"0\",\"track\":3,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2005-11-01\",\"album\":\"十一月的萧邦\",\"albumid\":14466,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img3.kuwo.cn/star/albumcover/500/32/88/1567952196.jpg\",\"songTimeMinutes\":\"05:00\",\"isListenFee\":true,\"pic120\":\"http://img3.kuwo.cn/star/albumcover/120/32/88/1567952196.jpg\",\"name\":\"发如雪\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_440614\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img3.kuwo.cn/star/albumcover/300/72/44/3648126291.jpg\",\"isstar\":0,\"rid\":440614,\"duration\":254,\"content_type\":\"0\",\"track\":2,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2008-10-15\",\"album\":\"魔杰座\",\"albumid\":29218,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img3.kuwo.cn/star/albumcover/500/72/44/3648126291.jpg\",\"songTimeMinutes\":\"04:14\",\"isListenFee\":true,\"pic120\":\"http://img3.kuwo.cn/star/albumcover/120/72/44/3648126291.jpg\",\"name\":\"给我一首歌的时间\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_52625817\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img1.kuwo.cn/star/starheads/300/10/6/294045140.jpg\",\"isstar\":0,\"rid\":52625817,\"duration\":244,\"content_type\":\"0\",\"track\":0,\"hasLossless\":false,\"hasmv\":0,\"releaseDate\":\"1970-01-01\",\"album\":\"\",\"albumid\":0,\"pay\":\"0\",\"artistid\":336,\"albumpic\":\"http://img1.kuwo.cn/star/starheads/500/10/6/294045140.jpg\",\"songTimeMinutes\":\"04:04\",\"isListenFee\":false,\"pic120\":\"http://img1.kuwo.cn/star/starheads/120/10/6/294045140.jpg\",\"name\":\"稻香(Demo)\",\"online\":1,\"payInfo\":{\"cannotDownload\":0,\"cannotOnlinePlay\":0}},{\"musicrid\":\"MUSIC_79479\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img2.kuwo.cn/star/albumcover/300/64/96/2266534336.jpg\",\"isstar\":0,\"rid\":79479,\"duration\":315,\"content_type\":\"0\",\"track\":5,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2003-07-31\",\"album\":\"叶惠美\",\"albumid\":1293,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img2.kuwo.cn/star/albumcover/500/64/96/2266534336.jpg\",\"songTimeMinutes\":\"05:15\",\"isListenFee\":true,\"pic120\":\"http://img2.kuwo.cn/star/albumcover/120/64/96/2266534336.jpg\",\"name\":\"东风破\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_324243\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img2.kuwo.cn/star/albumcover/300/36/9/13515874.jpg\",\"isstar\":0,\"rid\":324243,\"duration\":246,\"content_type\":\"0\",\"track\":5,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2007-11-02\",\"album\":\"我很忙\",\"albumid\":12449,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img2.kuwo.cn/star/albumcover/500/36/9/13515874.jpg\",\"songTimeMinutes\":\"04:06\",\"isListenFee\":true,\"pic120\":\"http://img2.kuwo.cn/star/albumcover/120/36/9/13515874.jpg\",\"name\":\"蒲公英的约定\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}},{\"musicrid\":\"MUSIC_118987\",\"artist\":\"周杰伦\",\"mvpayinfo\":{\"play\":0,\"vid\":0,\"down\":0},\"pic\":\"http://img3.kuwo.cn/star/albumcover/300/32/88/1567952196.jpg\",\"isstar\":0,\"rid\":118987,\"duration\":277,\"content_type\":\"0\",\"track\":6,\"hasLossless\":true,\"hasmv\":1,\"releaseDate\":\"2005-11-01\",\"album\":\"十一月的萧邦\",\"albumid\":14466,\"pay\":\"16711935\",\"artistid\":336,\"albumpic\":\"http://img3.kuwo.cn/star/albumcover/500/32/88/1567952196.jpg\",\"songTimeMinutes\":\"04:37\",\"isListenFee\":true,\"pic120\":\"http://img3.kuwo.cn/star/albumcover/120/32/88/1567952196.jpg\",\"name\":\"枫\",\"online\":1,\"payInfo\":{\"play\":\"1111\",\"local_encrypt\":\"1\",\"cannotDownload\":0,\"listen_fragment\":\"1\",\"cannotOnlinePlay\":0,\"feeType\":{\"song\":\"1\",\"vip\":\"1\"},\"down\":\"1111\"}}]},\"msg\":\"success\",\"profileId\":\"site\",\"reqId\":\"ec6ea9093a9fc4f010ae6e530b5a491a\"}";
        ReturnMessage returnMessage = JSONObject.parseObject(s, ReturnMessage.class);
        System.out.println("test");

    }

    public ReturnMessage download(String key, int pageNo, int pageSize, int rid) {
        ReturnMessage res = searcher.search(key, pageNo, pageSize);
        if (res.getCode() == 200) {
            if (!CollectionUtils.isEmpty(res.getData().getList())) {
                for (MusicInfo musicInfo : res.getData().getList()) {
                    //数据持久化 //
                    try {
                        if (musicInfo.getRid() == rid) {
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    musicPiP.save(musicInfo);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        }
        return res;
    }


    public static boolean equalPrefix(byte[] src, byte[] target) {
        if (src.length < target.length)
            return false;

        for (int i = 0; i < target.length; i++) {
            if (src[i] != target[i])
                return false;
        }

        return true;
    }

    @Test
    void contextLoads() {
        Semaphore semaphore = new Semaphore(20);
        for (int i = 1; i < 20; i++) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testSearch() {

    }

}
