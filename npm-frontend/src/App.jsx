import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import styled from 'styled-components';
import Logo from './Logo'
import Homepage from './Homepage';
import {RightArrowCircle} from 'styled-icons/boxicons-regular/RightArrowCircle';

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

const JoinEventText = styled.div`
  color: white;
  font-size: 50px;
  text-align: center;
`;

const JoinEventContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 150px;
  width: 450px;
`;

const JoinEventInput = styled.input`
  width: 150px;
  height: 62px;
  font-size: 50px;
  padding: 0 9px;
  text-align: center;
`;

const JoinEventCodeButton = styled.button`
  background-color: #2A77C9;
  color: white;
  height: 90px;
  width: 230px;
  font-size: 40px;
  border-radius: 13px;
  cursor: pointer;
  margin: 35px;
  border-color: #272727;
  font-weight: bold;
  box-shadow: inset 0px 0px 30px #000000, 13px 12px 20px -5px black;
  transition: width .5s, height .5s, font-size .5s, background-color .5s;
`;
const JoinEventUsernameButton = styled.button`
  background-color: #65D26E;
  color: white;
  height: 110px;
  width: 200px;
  font-size: 35px;
  border-radius: 13px;
  cursor: pointer;
  margin: 35px;
  border-color: #272727;
  font-weight: bold;
  box-shadow: inset 0px 0px 30px #000000, 13px 12px 20px -5px black;
  transition: width .5s, height .5s, font-size .5s, background-color .5s;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
`;



const JoinEventForm = styled.form`
  display: flex;
  align-items: center;
  flex-direction: column;
  margin: 60px 0;
`;

const Arrow = styled(RightArrowCircle)`
  height: 85px;
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
        <div>
          <TopBar><Logo isLogo={true}/></TopBar>
          <Background>
            <Switch>
              <Route exact path="/"> <Homepage /> </Route>
              <Route path="/join-code"> 
                <JoinEventContainer>
                  <JoinEventText>Please Enter Your Event Code</JoinEventText>
                  <JoinEventForm>
                    <JoinEventInput type="text" />
                    <JoinEventCodeButton>Join Event</JoinEventCodeButton>
                  </JoinEventForm>
                </JoinEventContainer>
              </Route>
              <Route path="/join-username">
                <JoinEventContainer>
                  <JoinEventText>Enter A Username</JoinEventText>
                  <JoinEventForm>
                    <JoinEventInput style={{width: "300px"}}type="text" />
                    <JoinEventUsernameButton>
                      <div>To The Music</div> 
                      <Arrow />
                    </JoinEventUsernameButton>
                  </JoinEventForm>
                </JoinEventContainer>
              </Route>
            </Switch>
          </Background>
        </div>
      </Router>
    );
  }
}

export default App;
