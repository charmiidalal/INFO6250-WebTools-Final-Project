import React, { useState, useEffect, Component } from "react"
import Form from "react-validation/build/form";
import "./createNews.scss";
import APIHelper from "./APIHelper.js"
import AuthService from "../../services/auth.service";
import axios from 'axios';

//     axios.post("http://localhost:8080/hw4/news.htm", {title,source,imageurl,author,description,newsurl,publishedat,category})
//     alert("News added successfully!")
//   }
// Add news button component which opens add news list form
class AddToDoTaskBtn extends Component {
  render() {
    return (
      <div>
        <h2 className="mainHeader">Post News</h2>
        {/* Onclick of add new to do task open input boxes */}
        <div id="addNewTaskBtn" onClick={e => this.props.toggleAddList(e)} title="Click here to add article details" className="addBtn">
          <label>Add New Article</label>
        </div>
      </div>
    );
  }
}

class ShowAddTask extends Component {
  render() {
    return (
      <div id="addItemDiv" className={this.props.addItems ? "" : "hiddenDiv"}>
         {/* input boxes for add title description and due date time */}
        <input type="text" className="inputElements" id="news-input" value={this.props.news_title} onChange={({ target }) => this.props.setNewsTitle(target.value)} placeholder="Title" />
        <input type="text" className="inputElements" id="news-desc" placeholder="Description" value={this.props.news_desc} onChange={({ target }) => this.props.setNewsDesc(target.value)} />
        <input type="text" className="inputElements" id="news-source" placeholder="Source" value={this.props.news_source} onChange={({ target }) => this.props.setNewsSource(target.value)} />
        <input type="text" className="inputElements" id="news-image-url" placeholder="Image URL" value={this.props.news_image_url} onChange={({ target }) => this.props.setNewsImageURL(target.value)} />
        <input type="text" className="inputElements" id="news-author" placeholder="Author" value={this.props.news_author} onChange={({ target }) => this.props.setNewsAuthor(target.value)} />
        <input type="text" className="inputElements" id="news-url" placeholder="Article URL" value={this.props.news_url} onChange={({ target }) => this.props.setNewsURL(target.value)} />
        <input type="date" className="inputElements" id="news-published-date" title="Click here to add due date" value={this.props.news_published_at} placeholder="Published At" onChange={({ target }) => this.props.setNewsPublishedAt(target.value)} />
        <div onClick={this.props.createNews} title="Click here to save task" className="addItemDiv saveBtn" >Save</div>
      </div>
    );
  }
}

class ListAllTasks extends Component {
  render() {
    return (
      <ul id="tasksListUL">
        {/* Lists all tasks from news list array */}
        {this.props.newsList.map(({ _id, title, description, source, image_url, author, article_url, published_at }, i) => (
          <li key={i}>
            <span onClick={e => this.props.updateNews(e, _id)} >{title}</span>
            {this.props.isOpened && this.props.curIndex === _id && (
              <div id={_id} className="listDetails">
                <div className="listDetailsDiv"><label>Description: </label><span>{description}</span></div>
                <div className="listDetailsDiv"><label>Source: </label><span>{source}</span></div>
                <div className="listDetailsDiv"><label>Image URL: </label><span>{image_url}</span></div>
                <div className="listDetailsDiv"> <label>Author:</label><span>{author}</span></div>
                <div className="listDetailsDiv"> <label>Article URL:</label><span>{article_url}</span></div>
                <div className="listDetailsDiv"> <label>Published At:</label><span>{published_at}</span></div>
              </div>
            )}
            {/* View button opens details of task and close button deletes the task */}
            <span className="viewDetails" onClick={e => this.props.toggleList(e, _id)}>View</span>
            <span className="close" onClick={e => this.props.deleteNews(e, _id)}>X</span>
          </li>
        ))}
      </ul>
    );
  }
}

function CreateNews() {
  //Defines the state and value of constant variables
  const [newsList, setNews] = useState([])
  const [news_title, setNewsTitle] = useState("")
  const [news_desc, setNewsDesc] = useState("")
  const [news_published_at, setNewsPublishedAt] = useState("")
  const [news_author, setNewsAuthor] = useState("")
  const [news_url, setNewsURL] = useState("")
  const [news_image_url, setNewsImageURL] = useState("")
  const [news_source, setNewsSource] = useState("")
  const [isOpened, setIsOpened] = useState(false);
  const [addItems, setAddItems] = useState(false);
  const [curIndex, setCurIndex] = useState("");
  // fetches all newss and sets it to news state variable
  useEffect(() => {
    const fetchNewsAndSetNews = async () => {
      const news = await APIHelper.getAllNews()
      setNews(news)
    }
    fetchNewsAndSetNews()
  }, [])
  // Toggles list when view button is clicked
  const toggleList = async (e, id) => {
    e.preventDefault()
    setCurIndex(id);
    setIsOpened(wasOpened => !wasOpened);
  }
  // Toggles list when view button is clicked
  const toggleAddList = async (e) => {
    e.preventDefault()
    setAddItems(wasOpened => !wasOpened);
  }
  // On save of create to-do list it sends the arguments to mongodb
  const createNews = async e => {
    e.preventDefault()
    if (!news_title) {
      alert("please enter something")
      return
    }
    if (newsList.some(({ title }) => title === news_title)) {
      alert(`Task: ${news_title} already exists`)
      return
    }
    const payload = {
      title: news_title,
      description: news_desc,
      source: news_source,
      publishedAt: news_published_at,
      imageURL: news_image_url,
      newsURL: news_url,
      author: news_author
    }
    const newNews = await APIHelper.createNews(payload)
    setNews([...newsList, newNews])
    setNewsTitle("")
    setNewsDesc("")
  }
  // It sends mongdodb call for task with the given id
  const deleteNews = async (e, id) => {
    try {
      e.stopPropagation()
      await APIHelper.deleteNews(id)
      setNews(newsList.filter(({ _id: i }) => id !== i))
    } catch (err) { }
  }
  // when clicked it sets task as completed
  const updateNews = async (e, id) => {
    e.stopPropagation()
    const payload = {
      status: !newsList.find(news => news._id === id).status,
    }
    const updatedNews = await APIHelper.updateNews(id, payload)
    setNews(newsList.map(news => (news._id === id ? updatedNews : news)))
  }
  // It calls all the components and sets the base structure
  return (
    <div className="mainDiv">
      <div id="outerDiv" className="outerDivClass">
        <AddToDoTaskBtn toggleAddList={toggleAddList} />
        <ShowAddTask addItems={addItems} setNewsTitle={setNewsTitle} setNewsURL ={setNewsURL} setNewsImageURL={setNewsImageURL}
          setNewsDesc={setNewsDesc} setNewsSource={setNewsSource} setNewsPublishedAt={setNewsPublishedAt} setNewsAuthor={setNewsAuthor}
          createNews={createNews} />
      </div>
      <ListAllTasks isOpened={isOpened} curIndex={curIndex} newsList={newsList}
        updateNews={updateNews} deleteNews={deleteNews} toggleList={toggleList} setNewsURL ={setNewsURL} setNewsImageURL={setNewsImageURL}
        setNewsSource={setNewsSource} setNewsTitle={setNewsTitle} setNewsDesc={setNewsDesc} setNewsPublishedAt={setNewsPublishedAt} setNewsAuthor={setNewsAuthor} />
    </div>
  )
}

export default CreateNews