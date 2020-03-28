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
import NewEvent from './CreateNewEvent';
import ShowCode from './ShowCode';
import GuestInterface from './GuestInterface';
import HostInterface from './HostInterface';

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
    const params = this.getHashParams();
    console.log(params);
    
    // Get event code out of storage if there is one
    const eventCode = JSON.parse(localStorage.getItem('eventCode'))

    this.state = {
      eventList: [],
      thisEvent: eventCode || '',
      validCode: false,
      codeInput: 0,
      name: ''
    }
  }

  componentDidMount() {
    // This syncs the event list with firebase. Any new data added to it will automatically update to firebase
    base.syncState(`eventList`, {
      context: this,
      state: 'eventList',
      asArray: true
    });
  }

  // This is run from CreateNewEvent and adds a new event to the event list
  addEvent = () => {
    const eventList = [...this.state.eventList];
    // Create a random 4 digit number for the event code
    const thisEvent = Math.floor(1000 + Math.random() * 9000);

    // Add the new event code to the event list along with the host name
    eventList.push({
      eventCode: thisEvent,
      // TODO: Update hostname with their spotify name
      hostName: `Host ${thisEvent}`
    });

    // Update the global event list with the new event code
    this.setState({ eventList });
    // Update state with the current event code
    this.setState({ thisEvent });
    // Update the name in state with the host's name
    this.setState({name: `Host ${thisEvent}` })

    // Store the event code in local storage so you won't lose it when you make changes to files
    localStorage.setItem('eventCode', JSON.stringify(thisEvent))
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
        this.setState({ thisEvent: codeInput})

        // Store the event code in local storage so it stays when you make changes to files 
        localStorage.setItem('eventCode', JSON.stringify(this.state.thisEvent))
      }
    })
  }

  // This is run from JoinEventUsername and adds the guest username to the guestList
  // TODO (maybe): update the connection with firebase so we don't have to loop through all the events all the time 
  addGuest = () => {
    const eventList = [...this.state.eventList];
    const thisEvent = this.state.thisEvent

    // Loop through each event in the event list
    eventList.forEach((e) => {
      // Check if the event code equals the current event
      if(e.eventCode === thisEvent) {
        // If guestList hasn't been created, create one
        if(e.guestList === undefined) {e.guestList = [];}
        // Add the name to the guestList
        e.guestList.push(this.state.name);
        // Update the eventList
        this.setState({ eventList });
      }
    })
  }

  // This is run when "Join Event" or "Create Event" are clicked on the homepage
  emptyLocalStorage = () => {
    // Clear out any event code from local storage
    localStorage.clear();
  }

  getHashParams() {
    let hashParams = {};
    let e, r = /([^&;=]+)=?([^&;]*)/g,
        q = window.location.hash.substring(1);
    e = r.exec(q)
    while (e) {
       hashParams[e[1]] = decodeURIComponent(e[2]);
       e = r.exec(q);
    }
    return hashParams;
  }

  render() {
    return (
      <Router>
          <TopBar>
            <Link to="/" style={{ textDecoration: 'none' }}>
              <Logo navBar={true}/>
            </Link>
          </TopBar>
          <Background>
            <Switch>
              <Route exact path="/"> <Homepage emptyLocalStorage={this.emptyLocalStorage} /> </Route>
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
                <NewEvent addEvent={this.addEvent} />
              </Route>
              <Route path="/show-code">
                <ShowCode thisEvent={this.state.thisEvent} />
              </Route>
              <Route path="/guest"> <GuestInterface /> </Route>
              <Route path="/host"> <HostInterface /> </Route>
              <Route path="/invalid-code"></Route>
            </Switch>
          </Background>
      </Router>
    );
  }
}

export default App;
