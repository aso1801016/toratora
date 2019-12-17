package jp.ac.asojuku.toratora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.method.TextKeyListener.clear
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        tora.setOnClickListener { onToratoraButtonTapped(it) }
        bba.setOnClickListener { onToratoraButtonTapped(it) }
        kato.setOnClickListener { onToratoraButtonTapped(it) }

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit{
            clear()

        }
    }

    fun onToratoraButtonTapped(view: View?) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("MY_TYPE",view?.id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}