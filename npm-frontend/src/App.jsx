import React, { Component } from 'react';
import styled from 'styled-components';
import SpotifyLogo from './spotifylogo.png';

const CreateEventButton = styled.button`
  font-weight: bold;
  color: #2A77C9;
  height: 90px;
  width: 190px;
  border-radius: 8px;
  font-size: 25px;
  cursor: pointer;
  margin: 15px;
  background-color: #EBEBEB;
  border-color: #272727;
  box-shadow: inset 0px 0px 21px 0px #272727, 13px 12px 20px -5px black;
  transition: width .5s, height .5s, font-size .5s;

  &:hover {
    height: 100px;
    width: 215px;
    font-size: 28px;
  }

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
  border-color: #272727;
  font-weight: bold;
  box-shadow: inset 0px 0px 30px #000000, 13px 12px 20px -5px black;
  transition: width .5s, height .5s, font-size .5s;

  &:hover {
    height: 170px;
    width: 380px;
    font-size: 55px;
  }
`;

const LogoJ = styled.div`
  color: #2A77C9;
  font-size: 250px;
  margin: -35px;
`;

const LogoTitle = styled.div`
  color: #EBEBEB;
  font-size: 50px;
  font-weight: bold;
`;

const HomePageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30px;
`;

const LogoContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Background = styled.div`
  background-color: #272727;
  height: 90vh;
  width: 100%;
`;

const Spotify = styled.img`
  height: 35px;
  transition: width .5s, height .5s, font-size .5s;

  ${CreateEventButton}:hover & {
    height: 40px;
  }
`;

const TopBar = styled.div`
  height: 65px;
  background-color: #EBEBEB;
`;

class App extends Component {
  render() {
    return (
      <div>
        <TopBar />
        <Background>
          <HomePageContainer>
            <LogoContainer>
              <LogoJ>J</LogoJ> 
              <LogoTitle>JAMBOX</LogoTitle>
            </LogoContainer>
              <JoinEventButton>Join Event</JoinEventButton>
              <CreateEventButton>Create Event <Spotify src={SpotifyLogo} /></CreateEventButton>
          </HomePageContainer>
        </Background>
      </div>
    );
  }
}

export default App;
