ALTER TABLE verification_codes
    ALTER COLUMN code SET NOT NULL,
    ALTER COLUMN expiry SET NOT NULL;
