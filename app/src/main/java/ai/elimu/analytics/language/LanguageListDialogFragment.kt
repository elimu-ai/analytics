package ai.elimu.analytics.language

import ai.elimu.analytics.MainActivity
import ai.elimu.analytics.R
import ai.elimu.analytics.util.SharedPreferencesHelper.storeLanguage
import ai.elimu.common.utils.ui.BaseBottomSheetDialogFragment
import ai.elimu.model.v2.enums.Language
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 * LanguageListDialogFragment.newInstance().show(getSupportFragmentManager(), "dialog");
</pre> *
 */
class LanguageListDialogFragment : BaseBottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_language_list_dialog_list_dialog,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = LanguageAdapter()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        isCancelable = false
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }


    private inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.fragment_language_list_dialog_list_dialog_item,
                parent,
                false
            )
        ) {
        val text: TextView = itemView.findViewById(R.id.text)
    }


    private inner class LanguageAdapter : RecyclerView.Adapter<ViewHolder>() {
        private val languages = Language.entries.toTypedArray()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val language = languages[position]
            holder.text.text = getString(
                R.string.language_selection_value,
                language.englishName,
                language.nativeName
            )
            holder.text.setOnClickListener {
                Timber.i("onClick")
                Timber.i("language: $language")
                context?.let {
                    storeLanguage(it, language)
                }
                val mainActivityIntent = Intent(context, MainActivity::class.java)
                startActivity(mainActivityIntent)
                activity?.finish()
            }
        }

        override fun getItemCount(): Int {
            return languages.size
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): LanguageListDialogFragment {
            val fragment = LanguageListDialogFragment()
            return fragment
        }
    }
}
