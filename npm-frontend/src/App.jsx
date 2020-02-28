import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import styled from 'styled-components';
import Logo from './Logo'
import Homepage from './Homepage';
import JoinEventCode from './JoinEventCode';
import JoinEventUsername from './JoinEventUsername';

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
  constructor() {
    super();
    this.state = {
      
    }
  }
  render() {
    return (
      <Router>
          <TopBar><Logo isLogo={true}/></TopBar>
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
                {/* Add error page for when a user doesn't have premium */}
              </Route>
            </Switch>
          </Background>
      </Router>
    );
  }
}

export default App;
