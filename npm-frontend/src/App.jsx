import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import styled from 'styled-components';
import Logo from './Logo'
import Homepage from './Homepage';
import JoinEventCode from './JoinEventCode';
import JoinEventUsername from './JoinEventUsername';
import ErrorPage from './ErrorPage';
import NewEvent from './CreateNewEvent';
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
    const params = this.getHashParams();
    console.log(params);
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
                <JoinEventCode />
              </Route>
              <Route path="/join-username">
                <JoinEventUsername />
              </Route>
              <Route path="/premium-error">
                <ErrorPage />
              </Route>
              <Route path="/create-new-event"> 
                <NewEvent />
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
