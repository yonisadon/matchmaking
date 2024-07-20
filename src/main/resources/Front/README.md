Controller: אחראי לטיפול בבקשות HTTP ולשלוח תגובות ללקוח.
Service: מכיל את הלוגיקה העסקית, ומטפל בשיחות לשכבת ה-Repository.
Repository: מתקשר עם מסד הנתונים ומבצע את השאילתות הנדרשות.
Model: מייצג את מבנה הנתונים של האובייקטים במערכת.


CREATE TABLE preferencesMen (
id INT AUTO_INCREMENT PRIMARY KEY,
menId INT,
preferredRegion VARCHAR(255),
preferredCommunity VARCHAR(255),
handkerchiefOrWig VARCHAR(255),
smokerOrNonSmoker VARCHAR(255),
kosherOrNonKosherDevice VARCHAR(255),
preferredStatus VARCHAR(255),
FOREIGN KEY (menId) REFERENCES men(id)
);
