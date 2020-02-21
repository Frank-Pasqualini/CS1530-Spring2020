import React, { Component } from 'react';
import styled from 'styled-components';
import SpotifyLogo from './spotifylogo.png';
import './App.css';

const CreateEventButton = styled.button`
  font-weight: bold;
  color: #2A77C9;
  height: 90px;
  width: 190px;
  border-radius: 8px;
  font-size: 25px;
  cursor: pointer;
  margin: 15px;
  `;

const JoinEventButton = styled.button`
  background-color: #2A77C9;
  color: white;
  height: 150px;
  width: 350px;
  font-size: 50px;
  border-radius: 13px;
  cursor: pointer;
  margin: 15px;
  border-color: #2A77C9;
  font-weight: bold;
`;

const LogoJ = styled.div`
  color: #2A77C9;
  font-size: 250px;
  margin: -35px;
`;

const LogoTitle = styled.div`
  color: white;
  font-size: 50px;
  font-weight: bold;
`;

const HomePageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 30px;
`;

const LogoContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Background = styled.div`
  background-color: #272727;
  height: 100vh;
`;

const Spotify = styled.img`
  height: 35px;
`;

const TopBar = styled.div`
  height: 65px;
  background-color: #EBEBEB;
`;

class App extends Component {
  render() {
    return (
      <Background>
        <TopBar />
        <HomePageContainer>
          <LogoContainer>
            <LogoJ>J</LogoJ> 
            <LogoTitle>JAMBOX</LogoTitle>
          </LogoContainer>
            <JoinEventButton>Join Event</JoinEventButton>
            <CreateEventButton>Create Event <Spotify src={SpotifyLogo} /></CreateEventButton>
        </HomePageContainer>
      </Background>
    );
  }
}

export default App;
