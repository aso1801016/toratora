package jp.ac.asojuku.toratora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    val kato = 0
    val tora = 1
    val bba = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getIntExtra("MY_TYPE",0)

        val myType: Int

        myType = when (id) {
            R.id.kato -> {
                myTypeImage.setImageResource(R.drawable.kato);kato
            };
            R.id.tora -> {
                myTypeImage.setImageResource(R.drawable.tora);tora
            };
            R.id.bba -> {
                myTypeImage.setImageResource(R.drawable.bba);bba
            };
            else -> kato;
        }
        val comType = getType()

        when (comType) {
            kato -> {
                comTypeImage.setImageResource(R.drawable.kato)
            }
            tora -> {
                comTypeImage.setImageResource(R.drawable.tora)
            }
            bba -> {
                comTypeImage.setImageResource(R.drawable.bba)
            }
        }
        val gameResult = (comType - myType + 3) % 3

        when(gameResult) {
            0 -> resultLabel.setText("引き分け")
            1 -> resultLabel.setText("勝利")
            2 -> resultLabel.setText("敗北...")
        }
        backbutton.setOnClickListener{ finish() }
        this.saveData(myType, comType, gameResult)
    }
    private fun saveData(myType:Int,comType:Int,gameResult:Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT",0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT",0)
        val lastComType = pref.getInt("LAST_COM_TYPE",0)
        val lastGameResult = pref.getInt("GAME_RESULT",-1)

        val edtWinningStreakCount:Int = when{
            (lastGameResult == 2 && gameResult == 2) -> (winningStreakCount +1)
            else -> 0
        }
        val editor = pref.edit()
        editor.putInt("GAME_COUNT",gameCount + 1)
            .putInt("WINNING_STREAK_COUNT",edtWinningStreakCount)
            .putInt("LAST_MY_TYPE",myType)
            .putInt("LAST_COM_TYPE",comType)
            .putInt("BEFORE_LAST_COM_TYPE",lastComType)
            .putInt("GAME_RESULT",gameResult)
            .apply()
    }
    private fun getType(): Int{
        var type = (Math.random() * 3).toInt()
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT",0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT",0)
        val lastmyType = pref.getInt("LAST_MY_TYPE",0)
        val lastComType = pref.getInt("LAST_COM_TYPE",0)
        val beforeLastComType = pref.getInt("BEFORE_LAST_COM_TYPE",0)
        val gameResult = pref.getInt("GAME_RESULT",-1)

        if(gameCount == 1){
            if(gameResult == 2){
                while(lastComType == type) {
                    type = (Math.random() * 3).toInt()
                }
            }else if (gameResult == 1){
                type = (lastmyType - 1 + 3) % 3
            }
        }else if(winningStreakCount > 0){
            if(beforeLastComType == lastComType){
                while(lastComType == type){
                    type = (Math.random() * 3).toInt()
                }
            }
        }
        return type
    }
}
