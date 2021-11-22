package cn.tucci.sso.server.core.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 雪花算法生成id<p>
 * id为long类型，长度64位<p>
 * 第一位为符号位，0<p>
 * 41位时间戳<p>
 * 10位workid, 0-1023<p>
 * 12位序列号，最大位4095<p>
 * <p>
 * 符号位    时间戳                                     workid   序列号
 * 0        00000000000000000000000000000000000000000 00000000 000000000000
 *
 * @author tucci.lee
 */
public final class SnowFlake {

    private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 开始时间
     */
    private static final long START_TIME = 1583574474481L;

    /**
     * 初始化的数据
     */
    private long workId;        //workid默认为0
    private long sequence = 0L; //序列号
    private long lastTime = -1L;//最后一次生成key的时间

    /**
     * 占用的位数
     */
    private static final long TIME_BIT = 41L;       //时间戳占用位数
    private static final long WORK_BIT = 10L;       //workid占用位数
    private static final long SEQUENCE_BIT = 12L;   //序列号占用位数

    /**
     * 左移的位数
     */
    private static final long WORK_LEFT_BIT = SEQUENCE_BIT;    //workid左移12位
    private static final long TIME_LEFT_BIT = WORK_BIT + SEQUENCE_BIT;   //时间戳左移22位

    /**
     * 最大值，即二进制多少位1计算出的十进制
     */
    private static final long SEQUENCE_MAX = ~(-1L << SEQUENCE_BIT);
    private static final long WORK_MAX = ~(-1L << WORK_BIT);

    /**
     * workId默认为0，分布式需要设置不同的workId
     */
    public SnowFlake() {
        this(0L);
    }

    public SnowFlake(long workId) {
        if (workId < 0 || workId > WORK_MAX) {
            log.error("workId Cannot be greater than " + WORK_MAX + " and less than 0");
            throw new IllegalArgumentException("workId Cannot be greater than " + WORK_MAX + " and less than 0");
        }
        this.workId = workId;
    }

    public synchronized long nextId() {
        long currentTime = System.currentTimeMillis();
        //避免程序运行中，回退系统时间。造成id重复
        if (currentTime < lastTime) {
            log.error("System time cannot be rollback!");
            throw new RuntimeException("System time cannot be rollback!");
        }

        //如果当前时间和最后生成id的时间相同，则sequence+1，
        //如果sequence+1后大于SEQUENCE_MAX，则sequence=0，当前时间等待下一毫秒
        if (currentTime == lastTime) {
            sequence = (sequence + 1) & SEQUENCE_MAX;
            if (sequence == 0L) {
                while (currentTime <= lastTime) {
                    currentTime = System.currentTimeMillis();
                }
            }
        } else {
            // 随机从0和1开始，避免都是从0开始都是偶数
            sequence = (int) (Math.random() * 2);
        }
        //更新最后生成id的时间为当前时间
        lastTime = currentTime;

        return (currentTime - START_TIME) << TIME_LEFT_BIT  //时间戳部分
                | workId << WORK_LEFT_BIT                   //workid部分
                | sequence;                                 //序列号部分;
    }

    public static void main(String[] args) throws InterruptedException {
        SnowFlake snowFlake = new SnowFlake();
        for (int i = 0; i < 100; i++) {
            Long id = snowFlake.nextId();
            System.out.println(id);
            Thread.sleep(1);
        }
    }
}