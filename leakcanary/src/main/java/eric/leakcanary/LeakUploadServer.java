package eric.leakcanary;

import android.util.Log;

import com.squareup.leakcanary.AbstractAnalysisResultService;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.HeapDump;
import com.squareup.leakcanary.LeakCanary;

/**
 *
 * Created by wenjun.zhong on 2017/7/5.
 */

public class LeakUploadServer  extends AbstractAnalysisResultService {

    @Override
    protected void onHeapAnalyzed(HeapDump heapDump, AnalysisResult result) {
        if ( !result.leakFound || result.excludedLeak ) {
            return;
        }
        Log.e("", "### *** onHeapAnalyzed in onHeapAnalyzed , dump dir :  " + heapDump.heapDumpFile.getParentFile().getAbsolutePath());
        String leakInfo = LeakCanary.leakInfo(this, heapDump, result, true);

        // 将内存泄漏日志上传服务器
        // TODO: 2017/7/5


    }
}
