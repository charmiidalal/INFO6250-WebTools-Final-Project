import React, { Component } from "react";
import Form from "react-validation/build/form";
import "./createNews.scss";
import AuthService from "../../services/auth.service";
import axios from 'axios';

export default class CreateNews extends Component {
  constructor(props) {
    super(props);
    // initial state of preferences
    this.state = { };
    this.handleSave = this.handleSave.bind(this);
  }

  // saves new user categories checked
  handleSave(e) {
    e.preventDefault();
    debugger;
    var title = document.getElementsByClassName("title")[0].value;
    var source = document.getElementsByClassName("source")[0].value;
    var imageurl = document.getElementsByClassName("imageurl")[0].value;
    var author = document.getElementsByClassName("author")[0].value;
    var description = document.getElementsByClassName("description")[0].value;
    var newsurl = document.getElementsByClassName("newsurl")[0].value;
    var publishedat = document.getElementsByClassName("publishedat")[0].value;
    var category = document.getElementsByClassName("category")[0].value;
    // updates user preferences
    axios.post("http://localhost:8080/hw4/news.htm", {title,source,imageurl,author,description,newsurl,publishedat,category})
    alert("News added successfully!")
  }

  handleCheck(val) {
    return this.state.categories.indexOf(val) > -1;
  }
  handleCntrCheck(val) {
    return this.state.countries.indexOf(val) > -1;
  }

  render() {
    return (
      <div className="loginPage">
        <div className="card card-container">
          <Form
            onSubmit={this.handleSave}
            ref={c => {
              this.form = c;
            }} class="loginForm1"
          >
            <div className="form-group prefMainDiv">
              <div className="prefHead"><h3>Post News</h3></div>
              <div>
                <label>Title: </label><input type="text" class="title" required/>
                <label>Source: </label><input type="text" class="source" required/>
                <label>Image URL: </label><input type="text" class="imageurl" required/>
                <label>Author: </label><input type="text" class="author" required/>
                <label>Description: </label><input type="text" class="description" required/>
                <label>News URL: </label><input type="text" class="newsurl" required/>
                <label>Published At: </label><input type="date" class="publishedat" required/>
                <label>Category: </label><input type="text" class="category" required/>
            
                {/* <div class="checkSection">
                  <div class="checkboxClass"> <input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("Philippines")} name="Philippines" cntrName="Philippines" class="country" /> <label>Philippines</label></div>
                   <div class="checkboxClass"><input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("Indonesia")} name="Indonesia" cntrName="Indonesia" class="country" /> <label>Indonesia</label></div>
                </div> */}
              </div>
              <div className="form-group newsButtons">
                <button
                  className="btn btn-primary btn-block"
                >
                  <span>Save</span>
                </button>
              </div>
            </div>

          </Form>
        </div>
      </div>
    );
  }
}