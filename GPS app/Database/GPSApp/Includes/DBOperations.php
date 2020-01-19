<?php

	class DbOperations{
		private $con;
		
		function __construct(){
			require_once dirname(__FILE__).'/DBConnect.php';
			$db = new DbConnects();
			$this->con = $db->connect();
		}
		
		
		//logs user into a certain account
		public function login($uname,$pword,$key){
			$information = array();
			$stmt = $this->con->prepare("SELECT `user_id` FROM `users` WHERE `userName` = ? AND `password` = ?");
			$stmt->bind_param("ss",$uname,$pword);
			$stmt->execute();
			$id = $stmt->get_result();
			
			if ($id->num_rows > 0){
				$val = $id->fetch_array(MYSQLI_NUM);
				$value = array_values($val)[0];
				$stmt = $this->con->prepare("UPDATE `users` SET `userKey`= ? WHERE `user_id` = ?");
				$stmt->bind_param("ss",$key,$value);
				$stmt->execute();
				
				$information['error'] = false;
				$information['message'] = "User Logged In";
			}else{
				$information['error'] = true;
				$information['message'] = "Incorrect Username or Password";
			}
			
			return $information;			
		}
		
		//updates the location related to the user account
		public function UpdateLocation($lat,$long,$uid,$key){
			$information = array();
			$stmt = $this->con->prepare("SELECT `user_id` FROM `users` WHERE `userKey` = ?");
			$stmt->bind_param("s",$key);
			$stmt->execute();
			$id = $stmt->get_result();
			
			if ($id->num_rows > 0){
				$val = $id->fetch_array(MYSQLI_NUM);
				$value = array_values($val)[0];
				
				if($value == $uid){
					$stmt = $this->con->prepare("UPDATE `location` SET `latitude`= ?,`longitude`= ? WHERE `user_id` = ?");
					$stmt->bind_param("sss",$lat,$long,$uid);
					$stmt->execute();
					
					$information['error'] = false;
					$information['message'] = "Location Updated";
					
				}else{
					$information['error'] = true;
					$information['message'] = "This account has been logged in on another device";
				}
			}else{
				$information['error'] = true;
				$information['message'] = "This account has been logged in on another device";
			}
			
			return $information;
		}
		
		public function GetLocation($uid,$key){
			$information = array();
			$stmt = $this->con->prepare("SELECT `user_id` FROM `users` WHERE `userKey` = ?");
			$stmt->bind_param("s",$key);
			$stmt->execute();
			$id = $stmt->get_result();
			
			if ($id->num_rows > 0){
				$val = $id->fetch_array(MYSQLI_NUM);
				$value = array_values($val)[0];
				
				if($value == $uid){
					$stmt = $this->con->prepare("SELECT `latitude`, `longitude` FROM `location` WHERE `user_id` = ?");
					$stmt->bind_param("s",$uid);
					$stmt->execute();
					
					$result = $stmt->get_result();
					$row = $result->fetch_array(MYSQLI_NUM);
					$latitude = array_values($row)[0];
					$longitude = array_values($row)[1];
					
					$information['error'] = false;
					$information['message'] = "Location Updated";
					$information['latitude'] = $latitude;
					$information['longitude'] = $longitude;
					
				}else{
					$information['error'] = true;
					$information['message'] = "This account has been logged in on another device";
					$information['latitude'] = "NA";
					$information['longitude'] = "NA";
				}
			}else{
				$information['error'] = true;
				$information['message'] = "This account has been logged in on another device";
				$information['latitude'] = "NA";
				$information['longitude'] = "NA";
			}
			
			return $information;
			
		}
		
		public function CreateUser($uname,$pword,$key){
			$information = array();
			$stmt = $this->con->prepare("SELECT `user_id` FROM `users` WHERE `userName` = ?");
			$stmt->bind_param("s",$uname);
			$stmt->execute();
			$id = $stmt->get_result();
			
			if ($id->num_rows > 0){
				$information['error'] = true;
				$information['message'] = "This username has already been used.";
			}else{
				$stmt = $this->con->prepare("INSERT INTO `users`(`user_id`, `userName`, `password`, `userKey`) VALUES (NULL, ?, ?, ?);");
				$stmt->bind_param("sss",$uname,$pword,$key);
				if($stmt->execute()){
					$information['error'] = false;
					$information['message'] = "Account Created";
				}else{
					$information['error'] = true;
					$information['message'] = "An Error Registering Account Has Occured.";
				}
			}
			
			return $information;
			
		}
		
	}
	
?>