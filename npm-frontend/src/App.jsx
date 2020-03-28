import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect
} from "react-router-dom";
import base from './base';
import styled from 'styled-components';
import Logo from './Logo';
import Homepage from './Homepage';
import JoinEventCode from './JoinEventCode';
import JoinEventUsername from './JoinEventUsername';
import ErrorPage from './ErrorPage';
import NewEvent from './CreateNewEvent';
import ShowCode from './ShowCode';
import GuestInterface from './GuestInterface';

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
      codeInput: 0
    }
  }

  componentDidMount() {
    base.syncState(`eventList`, {
      context: this,
      state: 'eventList',
      asArray: true
    });
  }

  addEvent = () => {
    const eventList = [...this.state.eventList];
    const thisEvent = Math.floor(1000 + Math.random() * 9000);
    eventList.push({
      eventCode: thisEvent,
      hostName: `Host ${thisEvent}`
    });

    // Update the global event list with the new event code
    this.setState({ eventList });
    // Update state with the current event code
    this.setState({ thisEvent });

    // Store the event code in local storage so you won't lose it when you make changes to files
    localStorage.setItem('eventCode', JSON.stringify(thisEvent))
  }

  updateCodeInput = (e) => {
    console.log("updating input");
    this.setState({
       codeInput: parseFloat(e.target.value)
    })
  }

  checkCode = () => {
    const codeInput = this.state.codeInput
    this.state.eventList.forEach((e) => {
      if(e.eventCode === codeInput) {
        this.setState({ validCode: true })
      }
    })
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
              <Route exact path="/"> <Homepage /> </Route>
              <Route path="/join-code"> 
                <JoinEventCode updateCodeInput={this.updateCodeInput} checkCode={this.checkCode} />
              </Route>
              <Route path="/join-username">
                {!this.state.validCode ? <Redirect to="/invalid-code" /> : <JoinEventUsername />}
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
              <Route exact path="/guest"> <GuestInterface /> </Route>
              <Route path="/invalid-code"></Route>
            </Switch>
          </Background>
      </Router>
    );
  }
}

export default App;
