<?php
	
	require_once '../Includes/DbOperations.php';
	$response = array();
	
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		
		if(isset($_POST['Latitude']) and isset($_POST['Longitude']) and isset($_POST['userID']) and isset($_POST['phoneKey'])){
			
			//operations
			
			$db = new DbOperations();
			$result = $db->UpdateLocation(
			$_POST['Latitude'],
			$_POST['Longitude'],
			$_POST['userID'],
			$_POST['phoneKey']);
			
			$response = $result;
			
		}else{
			$response['error'] = true;
			$response['id'] = "Required Fields Missing";
		}

	}else{
		$response['error'] = true;
		$response['id'] = "Invalid Request";
	}

echo json_encode($response);

?>