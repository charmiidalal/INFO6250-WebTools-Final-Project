import { BOOKMARK_ITEM, UNBOOKMARK_ITEM, GET_BOOKMARK_ITEMS } from './types';
import axios from "axios";
// This function bookmarks the item and stores in database
// gets state of bookmark items
export const bookmarkItem = item => (dispatch, getState) => {
  debugger;
  const { bookmarkItems } = getState().bookmarks;
  localStorage.setItem('bookmarks', JSON.stringify([item, ...bookmarkItems]));
  if (JSON.parse(localStorage.getItem("user") != null)) { // if no user logged in
    debugger;
    axios.post("http://localhost:8080/hw4/UpdateBookmarks.htm", { "username": JSON.parse(localStorage.getItem("user")).username, "bookmarks": JSON.stringify([item, ...bookmarkItems]) })
  }
  dispatch({
    type: BOOKMARK_ITEM,
    payload: item
  });
};

// This function unbookmarks the item and stores in database
export const unBookmarkItem = item => (dispatch, getState) => {
  const { bookmarkItems } = getState().bookmarks;
  const newBookmarkItems = bookmarkItems.filter(
    bookmarkItem => bookmarkItem !== item
  );
  localStorage.setItem('bookmarks', JSON.stringify(newBookmarkItems));
  if (JSON.parse(localStorage.getItem("user") != null)) { // changes bookmarked items
    axios.post("http://localhost:8080/hw4/UpdateBookmarks.htm", { "id": JSON.parse(localStorage.getItem("user")).id, "bookmarks": JSON.stringify(newBookmarkItems) })
  }
  dispatch({
    type: UNBOOKMARK_ITEM,
    payload: item
  });
};
// This function gets all items that rae bookmarked and stores in database
export const getBookmarkItems = () => {
  debugger;
  let bookmarkItems = localStorage.getItem('bookmarks');
  if (bookmarkItems === null || bookmarkItems === "") {
    bookmarkItems = [];
  } else {
    bookmarkItems = JSON.parse(bookmarkItems);
  }
  return {
    type: GET_BOOKMARK_ITEMS,
    payload: bookmarkItems
  };

};
