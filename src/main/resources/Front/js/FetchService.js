
class FetchService {
    static fetchData(url, method = 'GET', body = null) {
        const options = {
            method,
            headers: {
                'Content-Type': 'application/json'
            }
        };
        if (body) {
            options.body = JSON.stringify(body); // נשלח את המידע בגוף הבקשה
        }
console.log('url', url);

        return fetch(url, options)
            .then(response => {
                // בדיקת סטטוס התגובה
                if (!response.ok) {
                    if (response.status === 404) {
                        throw new Error("לא נמצאה רשומה לפי החיפוש.");
                    } else {
                        // זריקת שגיאה כללית עבור סטטוסים אחרים
                        throw new Error(`שגיאת HTTP! סטטוס: ${response.status}`);
                    }
                }
                return response.json();
            })
            .catch(error => {
                console.error('Error fetching search results:', error);
                // העברת השגיאה בחזרה
                throw error;
            });
    }
}