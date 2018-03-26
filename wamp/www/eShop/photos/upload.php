<?php

	if(isset($_POST['pathProduct']))
	{
		$targetImage = $_POST['pathProduct'];
	}

	if(isset($_POST['delete'])){
		$deleteImages = $_POST['delete'];
	}
	
	/*if(isset($_POST['copy'])){
		$copyDirectory = $_POST['copy'];
	}*/

	if(isset($_POST['image'])){
		$tempImage = $_POST['image'];
	}

	/*if(isset($_POST['typeCopy'])){
		$type = $_POST['typeCopy'];
	}*/

	if(isset($_POST['name'])){
		$name = $_POST['name'];
	}

	//$tempFolder = 'temp/';
	

	if (!file_exists($targetImage)) {
		mkdir($targetImage, 0777, true);
	}

	/*if(isset($deleteImages) && isset($targetImage) && $deleteImages == 'delete'){
		$files = glob($targetImage.'/*'); // get all file names
		foreach($files as $file){ // iterate files
		  if(is_file($file))
		    unlink($file); // delete file
		}
	}*/

	if(isset($deleteImages) && isset($targetImage) && $deleteImages == 'deleteOneImage' && isset($name)){
		$files = glob($targetImage.'/'.$name);
		foreach ($files as $file) {
			if(is_file($file)){
				unlink($file);
			}
		}
	}

	
	if (isset($_FILES['userfile']['tmp_name']) && is_uploaded_file($_FILES['userfile']['tmp_name'])) {
		echo $targetImage.$_FILES['userfile']['name']." 0000000000000000000000000000000 ";

		echo $_FILES['userfile']['tmp_name']." 1111111111111111111111111111111111 ";

	    move_uploaded_file ($_FILES['userfile']['tmp_name'], $targetImage.$_FILES['userfile']['name']);
	}

	/*if(isset($copyDirectory) && isset($targetImage) && isset($tempImage) && isset($type) && $copyDirectory == 'copy' && $type == 'temp'){
		if(!file_exists($tempFolder)){
			mkdir($tempFolder, 0777, true);
		}
		
		if(file_exists($targetImage.$tempImage)){
			copy($targetImage.$tempImage, $tempFolder.$tempImage);
		}
	}*/

	/*if(isset($copyDirectory) && isset($targetImage) && isset($tempImage) && isset($type) && $copyDirectory == 'copy' && $type != 'temp'){
		
		if(file_exists($tempFolder.$tempImage)){
			copy($tempFolder.$tempImage, $targetImage.$type);
		}
	}*/
?>