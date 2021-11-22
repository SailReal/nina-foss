package de.ninafoss.presentation.ui.bottomsheet

import de.ninafoss.generator.BottomSheet
import de.ninafoss.presentation.R

@BottomSheet(R.layout.dialog_bottom_sheet_message_action)
class LocationContentActionBottomSheet : BaseBottomSheet<LocationContentActionBottomSheet.Callback>() {

	interface Callback {

		fun onCreateNewFolderClicked()
		fun onCreateNewTextFileClicked()
	}

	override fun setupView() {
		/*val folder = requireArguments().getSerializable(FOLDER_ARG) as CloudFolderModel

		title.text = String.format(getString(R.string.screen_file_browser_actions_title), folderPath(folder))

		create_new_folder.setOnClickListener {
			callback?.onCreateNewFolderClicked()
			dismiss()
		}
		upload_files.setOnClickListener {
			callback?.onUploadFilesClicked(folder)
			dismiss()
		}
		create_new_text_file.setOnClickListener {
			callback?.onCreateNewTextFileClicked()
			dismiss()
		}*/
	}

	/*private fun folderPath(folder: CloudFolderModel): String {
		val vault = folder.vault()
		return if (vault == null) {
			folder.path
		} else {
			vault.path + folder.path
		}
	}*/

	companion object {

		private const val FOLDER_ARG = "folder"
		/*fun newInstance(folder: CloudFolderModel): VaultContentActionBottomSheet {
			val dialog = VaultContentActionBottomSheet()
			val args = Bundle()
			args.putSerializable(FOLDER_ARG, folder)
			dialog.arguments = args
			return dialog
		}*/
	}
}
