import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const ChooseDefaultButton = styled.button`
  background-color: limegreen;
  color: white;
  height: 150px;
  width: 230px;
  font-size: 40px;
  border-radius: 13px;
  cursor: pointer;
  margin: 35px;
  border-color: #272727;
  font-weight: bold;
  box-shadow: inset 0px 0px 30px #000000, 13px 12px 20px -5px black;
  transition: width .5s, height .5s, font-size .5s, background-color .5s;

  &:hover {
    height: 170px;
    width: 250px;
    font-size: 47px;
    background-color: limegreen;
  }
  `;
const ChooseExsistingButton = styled.button`
  background-color: #2A77C9;
  color: white;
  height: 150px;
  width: 230px;
  font-size: 40px;
  border-radius: 13px;
  cursor: pointer;
  margin: 35px;
  border-color: #272727;
  font-weight: bold;
  box-shadow: inset 0px 0px 30px #000000, 13px 12px 20px -5px black;
  transition: width .5s, height .5s, font-size .5s, background-color .5s;

  &:hover {
    height: 170px;
    width: 250px;
    font-size: 47px;
    background-color: #2A77D6;
  }
  `;

const CreateEventText = styled.div`
  color: white;
  font-size: 50px;
  text-align: center;
`;

const CreateEventContainer = styled.div`
  display: flex;
  flex-direction: column;
  text-align: center;
  align-items: center;
  padding: 70px;
  width: 450px;
`;

class CreateNewEvent extends Component {
  render() {
    return (
      <CreateEventContainer>
        <CreateEventText>Please Choose a Playlist</CreateEventText>
        <Link to="/choose-playlist"> 
          <ChooseExsistingButton onClick={this.props.addEvent}>Personal </ChooseExsistingButton>
          <ChooseDefaultButton onClick={this.props.addEvent}>Spotify Default</ChooseDefaultButton>
        </Link>
      </CreateEventContainer>
    )
  }
}
  
  export default CreateNewEvent; 