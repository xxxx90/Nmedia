package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

val txt =intent.getStringExtra(Intent.EXTRA_TEXT)

        binding.edit.setText(txt)

        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isNotBlank()) {
                setResult(RESULT_OK, Intent().apply {
                    putExtra(KEY_TEXT, text)
                    putExtra(KEY_ID, text)

                })
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }
    }

    companion object {
        const val KEY_TEXT = "post_text"
        const val KEY_ID = "post_id"

    }
}

object NewPostContract : ActivityResultContract<String?, String?>() {
//    override fun createIntent(context: Context, input: String?): Intent =
//        Intent(context, NewPostActivity::class.java)

       override fun createIntent(context: Context, input: String?): Intent {
        val intent = Intent(context, NewPostActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? =

            intent?.getStringExtra(NewPostActivity.KEY_TEXT)


}