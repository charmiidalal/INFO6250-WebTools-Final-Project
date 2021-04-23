const API_URL = "http://localhost:8080/hw4/"

async function createNews(task) {
  const response = await fetch(API_URL+"CreateNews.htm", {
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, *cors, same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'same-origin', // include, *same-origin, omit
    headers: {
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify(task) // body data type must match "Content-Type" header
  });
  return response.json();
}

async function deleteNews(id) {
  return fetch(API_URL + id, {
    method: 'delete'
  }).then(response =>
    response.json().then(json => {
      return json;
    })
  );
}

async function updateNews(id, payload) {
  const response = await fetch(API_URL+id, {
    method: 'PUT', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, *cors, same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'same-origin', // include, *same-origin, omit
    headers: {
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify(payload) // body data type must match "Content-Type" header
  });
  return response.json();
}

async function getAllNews() {
  const response = await fetch(API_URL);
  const jsonData = await response.json();
  return jsonData;
}

export default { createNews, deleteNews, updateNews, getAllNews }