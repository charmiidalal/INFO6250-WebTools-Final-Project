import {
  BOOKMARK_ITEM,
  UNBOOKMARK_ITEM,
  GET_BOOKMARK_ITEMS
} from '../actions/types';

// initial state of bookmarks
const initialState = {
  bookmarkItems: []
};
// Reducer file manages the current state of bookmark
// actions for bookmark/unbookmark item
export default (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case BOOKMARK_ITEM:
      return {
        ...state,
        bookmarkItems: [payload, ...state.bookmarkItems]
      };
    case UNBOOKMARK_ITEM:
      return {
        ...state,
        bookmarkItems: state.bookmarkItems.filter(
          item => item.newsID !== payload.newsID
        )
      };
    case GET_BOOKMARK_ITEMS:
      return {
        ...state,
        bookmarkItems: payload
      };

    default:
      return state;
  }
};
