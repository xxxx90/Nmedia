package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostContract) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContentAndSave(result)

        }


        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
               newPostLauncher.launch(post.content)

                      viewModel.edit(post)
            }

            override fun onShare(post: Post) {
                viewModel.share(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)

                }
                val chooser = Intent.createChooser(intent, getString(R.string.app_name))
                startActivity(chooser)
            }

            override fun play(post: Post) {
                val txt = post.videoUrl
                val inten= Intent(Intent.ACTION_VIEW,Uri.parse(txt))
                startActivity(inten)
            }





//            override fun cancell() {
//                viewModel.cancell()
//            }


        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
        viewModel.edited.observe(this) { post ->
          //  if (post.id != 0L) {
             //   binding.groupOnEdit.isVisible = true


          //  }
        }

        binding.save.setOnClickListener {

            newPostLauncher.launch("")
        }

        binding.groupVideo.setOnClickListener {
            newPostLauncher.launch("")
            val txt="https://www.youtube.com/shorts/-WNg-R3AkDY"
            val intent= Intent(Intent.ACTION_VIEW,Uri.parse(txt))
            startActivity(intent)


        }


    }
}



