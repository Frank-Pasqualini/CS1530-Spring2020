import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect
} from "react-router-dom";
import base from './base.js';
import styled from 'styled-components';
import Logo from './Logo';
import Homepage from './Homepage';
import JoinEventCode from './JoinEventCode';
import JoinEventUsername from './JoinEventUsername';
import ErrorPage from './ErrorPage';
import CreateNewEvent from './CreateNewEvent';
import ShowCode from './ShowCode';
import GuestInterface from './GuestInterface';
import HostInterface from './HostInterface';
import JoinCodeError from './JoinCodeError';

const Background = styled.div`
  background-color: #272727;
  min-height: 100vh;
  width: 100%;
  display: flex;
  justify-content: center;
`;

const TopBar = styled.div`
  height: 60px;
  background-color: #EBEBEB;
  display: flex;
`;

class App extends Component {
  constructor(){
    super();

    // Get event code out of storage if there is one
    const eventCode = JSON.parse(localStorage.getItem('eventCode'));
    const currName = JSON.parse(localStorage.getItem('currName'));
    const accessToken = JSON.parse(localStorage.getItem('accessToken'));
    const songData = JSON.parse(localStorage.getItem('songData'));

    this.state = {
      eventList: [],
      currEventCode: eventCode || '',
      validCode: false,
      codeInput: 0,
      name: currName || '',
      accessToken: accessToken || '', 
      songData: songData || ''
    }
  }

  componentDidMount() {
    // This syncs the event list with firebase. Any new data added to it will automatically update to firebase
    base.syncState(`eventList`, {
      context: this,
      state: 'eventList',
      asArray: true
    });
    base.syncState(`songData`, {
      context: this,
      state: 'songData'
    });
  }

  updateEventCode = (code) => {
    this.setState({ currEventCode: code });
  }

  // This is run from CreateNewEvent and adds a new event to the event list
  addEvent = (code) => {
    const eventList = [...this.state.eventList];

    // Add the new event code to the event list along with the host name
    eventList.push({
      eventCode: code,
      // TODO: Update hostname with their spotify name
      hostName: `Host ${code}`
    });

    // Update the global event list with the new event code
    this.setState({ eventList });
    // Update state with the current event code
    this.setState({ currEventCode: code });
    // Update the name in state with the host's name
    this.setState({name: `Host ${code}` });

    // Store the event code in local storage so you won't lose it when you make changes to files
    localStorage.setItem('eventCode', JSON.stringify(code));
  }

  // This is run from JoinEventCode and updates state with the input from the textbox
  updateCodeInput = (e) => {
    this.setState({
       codeInput: parseFloat(e.target.value)
    })
  }

  // This is run from JoinEventUsername and updates state with the input from the textbox
  updateNameInput = (e) => {
    this.setState({
       name: e.target.value
    })
    // Set guest name in local storage so it stays even when page is refreshed
    localStorage.setItem('currName', JSON.stringify(e.target.value));
  }

  // This is run from JoinEventCode and checks if the submitted event code is valid 
  checkCode = () => {
    const codeInput = this.state.codeInput

    // Loop through each event in the event list
    this.state.eventList.forEach((e) => {
      // Check if the event code equals the submitted value
      if(e.eventCode === codeInput) {
        // Update the validCode status to true so that the correct page renders (the username page can now render)
        this.setState({ validCode: true })
        // Update the current event code 
        this.setState({ currEventCode: codeInput})

        // Store the event code in local storage so it stays when you make changes to files 
        localStorage.setItem('eventCode', JSON.stringify(this.state.currEventCode))
      }
    })
  }

  // This is run from JoinEventUsername and adds the guest username to the guestList
  // TODO (maybe): update the connection with firebase so we don't have to loop through all the events all the time 
  addGuest = () => {
    const eventList = [...this.state.eventList];
    const currEventCode = this.state.currEventCode    

    // Loop through each event in the event list
    eventList.forEach((e) => {
      // Check if the event code equals the current event
      if(e.eventCode === currEventCode) {
        // If guestList hasn't been created, create one
        if(e.guestList === undefined) {e.guestList = [];}
        // Add the name to the guestList
        e.guestList.push(this.state.name);
        // Update the eventList
        this.setState({ eventList });

        console.log(this.state.name);
      }
    })
    // Update the backend with the new user
    fetch(`http://localhost:8080/api/add_user?eventCode=${this.state.currEventCode}&userId=${this.state.name}`);
  }

  // This is run when "Join Event" or "Create Event" are clicked on the homepage
  emptyLocalStorage = () => {
    console.log('emptying storage')
    // Clear out any event code from local storage
    localStorage.clear();
  }

  updateToken = (token) => {
    // Store the access token
    localStorage.setItem('accessToken', JSON.stringify(token));
    this.setState({ accessToken: token });
  }

  updateSongs = (data) => {
    this.setState({ songData: data })
    localStorage.setItem('songData', JSON.stringify(data));
  }

  render() {
    return (
      <Router>
          <TopBar>
            <Link to="/" onClick={this.emptyLocalStorage} style={{ textDecoration: 'none' }}>
              <Logo navBar={true} />
            </Link>
          </TopBar>
          <Background>
            <Switch>
              <Route exact path="/"> <Homepage /> </Route>
              <Route path="/join-code"> 
                <JoinEventCode updateCodeInput={this.updateCodeInput} checkCode={this.checkCode} />
              </Route>
              <Route path="/join-username">
                {!this.state.validCode ? <Redirect to="/invalid-code" /> : <JoinEventUsername addGuest={this.addGuest} updateNameInput={this.updateNameInput} />}
              </Route>
              <Route path="/premium-error">
                <ErrorPage />
              </Route>
              <Route path="/create-new-event">
                <CreateNewEvent updateToken={this.updateToken} />
              </Route>
              <Route path="/show-code">
                <ShowCode currEventCode={this.state.currEventCode} accessToken={this.state.accessToken} addEvent={this.addEvent} updateEventCode={this.updateEventCode} />
              </Route>
              <Route path="/guest"> 
                <GuestInterface 
                  songData={this.state.songData} 
                  currEventCode={this.state.currEventCode} 
                  updateSongs={this.updateSongs} 
                  userID={this.state.name} 
                /> 
              </Route>
              <Route path="/host">
                <HostInterface 
                  accessToken={this.state.accessToken} 
                  currEventCode={this.state.currEventCode} 
                  updateSongs={this.updateSongs} 
                  songData={this.state.songData} 
                  /> 
                </Route>
              <Route path="/invalid-code"> 
                <JoinCodeError />
              </Route>
            </Switch>
          </Background>
      </Router>
    );
  }
}

export default App;
