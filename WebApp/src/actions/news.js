import axios from '../axios';
import {
  SET_NEWS_LOADING,
  SET_SOURCE_LOADING,
  GET_NEWS_SOURCE,
  NEWS_SOURCE_ERROR,
  CHANGE_THEME,
  SET_THEME,
  TOP_NEWS_ERROR,
  SET_TOP_NEWS,
  CLEAR_TOP_NEWS
} from './types';

// Set Theme
export const setTheme = () => {
  let localTheme = localStorage.getItem('theme');

  if (localTheme === undefined || localTheme === null) {
    localTheme = 'light';
    document.body.style.backgroundColor = '#ccc';
    localStorage.setItem('theme', localTheme);
  }

  if (localTheme === 'dark') {
    document.body.style.backgroundColor = '#ccc';
  } else {
    document.body.style.backgroundColor = '#fff';
  }
  return {
    type: SET_THEME,
    payload: localTheme
  };
};

// Change theme
export const changeTheme = theme => {
  localStorage.setItem('theme', "light");
  if (theme === 'dark') {
    document.body.style.backgroundColor = '#ccc';
  } else {
    document.body.style.backgroundColor = '#fff';
  }
  return {
    type: CHANGE_THEME,
    payload: theme
  };
};

// Set Source loading
export const setSourceLoading = () => {
  return {
    type: SET_SOURCE_LOADING
  };
};

// Set News Loading
export const setNewsLoading = () => {
  return {
    type: SET_NEWS_LOADING
  };
};

// Get News Source
export const getNewsSource = () => async dispatch => {
  dispatch(setSourceLoading());
  try {
    const newsSource = await axios.get(`GetSourceList.htm`);
    if (newsSource) {
      dispatch({
        type: GET_NEWS_SOURCE,
        payload: newsSource.data.sources
      });
    }
  } catch (error) {
    dispatch({
      type: NEWS_SOURCE_ERROR
    });
  }
};

// Set Top News
export const setTopNews = (url, page) => async (dispatch, getState) => {
  dispatch(setNewsLoading());

  try {
    if (page === 1) {
      dispatch({ type: CLEAR_TOP_NEWS });
    }
    //const { pageSize } = getState().news;
    const newsItems = await axios.get(
      //  `${url}?page=${page}&pageSize=${pageSize}`
     `${url}`
    );
    if (newsItems) {
      dispatch({
        type: SET_TOP_NEWS,
        payload: newsItems.data
      });
    }
  } catch (error) {
    dispatch({
      type: TOP_NEWS_ERROR
    });
  }
};

//  Clear top news
export const clearTopNews = () => {
  return {
    type: CLEAR_TOP_NEWS
  };
};
