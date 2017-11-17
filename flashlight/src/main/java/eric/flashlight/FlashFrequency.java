package eric.flashlight;

import java.util.Arrays;

/**
*
 * Created by wenjun.zhong on 2017/8/4.
 */

public enum FlashFrequency {

    ALWAYS_BRIGHT(0), FREQUENCY_SOS(3, 300, 1000, 300), FREQUENCY_1(1, 3000), FREQUENCY_2(1, 1000), FREQUENCY_3(1, 100);

    private long[] timeFrequency;
    private int count;
    private int index;

    FlashFrequency(int parameterCount, long... times ){
        if(parameterCount > 0){
            timeFrequency = new long[parameterCount];
            int index = 0;
            for (long time : times){
                timeFrequency[index++] = time;
            }
        }
        index = 0;
        count = parameterCount;
    }

    public void reset(){
        index = 0;
        count = 0;
        if(timeFrequency != null){
            count = timeFrequency.length;
        }
    }

    public long nextFrequency(){
        if(count <= 0 || timeFrequency == null){
            return 0;
        }

        if(index > (count - 1)){
            index = 0;
        }
        return timeFrequency[index++];
    }

    @Override
    public String toString() {
        return "FlashFrequency{" +
                "timeFrequency=" + (timeFrequency == null ? "null" : Arrays.toString(timeFrequency)) +
                '}';
    }
}
