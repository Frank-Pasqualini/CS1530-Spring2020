import React, { Component } from 'react';
import styled from 'styled-components'
import { createGlobalStyle } from "styled-components";

const JamBoxFont = createGlobalStyle`
  @import url('https://fonts.googleapis.com/css?family=Roboto&display=swap');
  body {
    font-family: 'Roboto', sans-serif;
  }
`
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
    font-size: 45px;
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

class App extends Component {
  render() {
    return (
     <Background>
     <JamBoxFont />
     <HomePageContainer>
      <LogoContainer>
      <LogoJ> J </LogoJ> 
      <LogoTitle> JAMBOX </LogoTitle>
      </LogoContainer>
      <JoinEventButton> Joint Event </JoinEventButton>
      <CreateEventButton> Create Event </CreateEventButton>
     </HomePageContainer>
     </Background>
    );
  }
}

export default App;
