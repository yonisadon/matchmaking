
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
                if (!response.ok) {
                    return response.text().then(text => {
                    throw new Error(`HTTP error! status: ${response.status}, message: ${text}`);
                    });
                }
                return response.json();
            });
    }
}
