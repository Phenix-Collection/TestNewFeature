package eric.ping

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var isRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable {
            kotlin.run {
                while (isRun) {
                    var rtt = PingUtil.getAvgRTT("163.177.151.110", 4, 10)

                    System.out.println("rtt: " + rtt)
                }
            }
        }).start()
    }

    override fun onStop() {
        super.onStop()
        isRun = false
    }
}
