<?php

	require_once 'C:/xampp/GPSApp/Includes/DBOperations.php';

	Class TestDBOperations extends \PHPUnit_Framework_TestCase{
		
		public function testUpdateLocation(){
			$db = new DbOperations();
			
			$result = $db->UpdateLocation("-30.777","101.747","1","abcd");
			//echo $result['error'];
			$expected = false;
			$this->assertFalse($result['error']);
			
		}
		
		public function testGetLatitude(){
			$db = new DbOperations();
			
			$result = $db->UpdateLocation("-30.77","101.747","1","abcd");
			$result2 = $db->GetLocation("1", "abcd");
			$this->assertSame($result2['latitude'], -30.77);
			
		}
		
		public function testGetLongitude(){
			$db = new DbOperations();
			
			$result = $db->UpdateLocation("-30.77","101.747","1","abcd");
			$result2 = $db->GetLocation("1", "abcd");
			$this->assertSame($result2['longitude'], 101.747);
			
		}
		
		
		
	}

	


?>