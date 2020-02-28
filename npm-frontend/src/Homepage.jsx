import React from 'react'
import styled from 'styled-components';
import SpotifyLogo from './spotifylogo.png';
import Logo from './Logo'

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
  transition: width .5s, height .5s, font-size .5s, background-color .5s;

  &:hover {
    height: 105px;
    width: 220px;
    font-size: 29px;
    background-color: #EBEBF1;
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
  transition: width .5s, height .5s, font-size .5s, background-color .5s;

  &:hover {
    height: 170px;
    width: 380px;
    font-size: 55px;
    background-color: #2A77D6;
  }
`;

const HomePageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30px;
`;

const Spotify = styled.img`
  height: 35px;
  transition: width .5s, height .5s, font-size .5s;

  ${CreateEventButton}:hover & {
    height: 40px;
  }
`;

function Homepage(props) {
  return (
    <HomePageContainer>
      <Logo isLogo={false} />
      <JoinEventButton>Join Event</JoinEventButton>
      <CreateEventButton>Create Event <Spotify src={SpotifyLogo} /></CreateEventButton>
    </HomePageContainer>
  )
}

export default Homepage; 