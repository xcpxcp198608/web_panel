-- store process

-- 在console中将换行;转换为$$防止存储过程和mysql语法冲突
DELIMITER $$


CREATE PROCEDURE `create_rent_user`
  (IN v_client_key VARCHAR(20),  -- in定义输入参数， out定义输出结果
  IN v_mac VARCHAR(17),
  IN v_first_name VARCHAR(30),
  IN v_last_name VARCHAR(30),
  IN v_email VARCHAR(50),
  IN v_phone VARCHAR(20),
  IN v_payment_type VARCHAR(10),
  IN v_card_number VARCHAR(16),
  IN v_expires_date VARCHAR(4),
  IN v_security_key VARCHAR(3),
  IN v_activate_time VARCHAR(30),
  IN v_expires_time VARCHAR(30),
  OUT v_result TINYINT)
  BEGIN
    DECLARE insert_count INT DEFAULT 0 ;
    START TRANSACTION ;
    INSERT IGNORE INTO auth_rent_user
      (clientKey, mac, firstName, lastName, email, phone, paymentType, cardNumber, expirationDate,
      securityKey, activateTime, expiresTime)
      VALUES (v_client_key, v_mac, v_first_name, v_last_name, v_email, v_phone, v_payment_type,
        v_card_number, v_expires_date, v_security_key, v_activate_time, v_expires_time);
    SELECT row_count() INTO insert_count; -- 返回插入数据影响的行数， >0 -> 成功， <=0 -> 失败
    IF (insert_count = 0) THEN
      ROLLBACK ;
      SET v_result = 0;
    ELSEIF (insert_count < 0) THEN
      ROLLBACK ;
      SET v_result = -1;
    ELSE
      UPDATE device_rent
        SET is_rented = 1
        WHERE mac = v_mac;
      SELECT row_count() INTO insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK ;
        SET v_result = 0;
      ELSEIF (insert_count < 0) THEN
        ROLLBACK ;
        SET v_result = -1;
      ELSE
        COMMIT ;
        SET v_result = 1;
      END IF ;
    END IF;
  END $$


DELIMITER ;

SET @v_result = -2;

CALL create_rent_user('12312', 'mac', '参数', @v_result);

SELECT @v_result;