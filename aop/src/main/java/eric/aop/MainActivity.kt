package eric.aop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import eric.aop.aspectj.AspectJActivity

class MainActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.aspectj_button).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.aspectj_button){
            val intent : Intent = Intent(this, AspectJActivity::class.java)
            startActivity(intent)
        }
    }
}
