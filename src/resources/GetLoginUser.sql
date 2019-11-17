SELECT id as userId
, userName
, userRoleName
FROM tblUser 
JOIN cfgUserRole USING(userRole) 
WHERE userName=:userName AND password=:userPassword
				