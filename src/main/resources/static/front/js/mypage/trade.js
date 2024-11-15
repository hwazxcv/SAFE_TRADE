window.addEventListener("DOMContentLoaded", function() {
    ClassicEditor
    		.create( document.querySelector( '#content' ), {
    		    height: 350
    		} )
    		.then( editor => {
    			window.editor = editor;
    			editor.ui.view.editable.element.style.height = '450px';
    		} )
    		.catch( err => {
    			console.error( err.stack );
    		} );

});

function insertEditor(imgUrl) {
    editor.execute( 'insertImage', { source: imgUrl } );
}

function fileUploadCallback(files) {
    if (!files || files.length == 0) {
        return;
    }

    const { fileManager } = commonLib;


    const tplEditor = document.getElementById("tpl_editor").innerHTML;

    const editorImages = document.getElementById("editor_images");
    const domParser = new DOMParser();

    for (const file of files) {
        let html = tplEditor, targetEl = editorImages;

         /** 이미지 에디터 본문 삽입 */
         insertEditor(file.fileUrl);

        html = html.replace(/\[id\]/gm, file.id)
                    .replace(/\[url\]/gm, file.thumbsUrl[0])
                    .replace(/\[fileName\]/gm, file.fileName)
                    .replace(/\[orgUrl\]/gm, file.fileUrl);

        const dom = domParser.parseFromString(html, "text/html");
        const fileEl = dom.querySelector("body > *");
        targetEl.appendChild(fileEl);

        const removeEl = fileEl.querySelector(".remove");
        removeEl.addEventListener("click", function() {
            if (!confirm('정말 삭제하시겠습니까?')) {
                return;
            }

            const id = this.dataset.id;
            fileManager.delete(id);
        });

        const insertEditorEl = fileEl.querySelector(".insert_editor");
        if (insertEditorEl) {
            insertEditorEl.addEventListener("click", function() {
                insertEditor(this.dataset.url);
            });
        }
    }
}