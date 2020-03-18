import React from 'react'
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
    font-size: 40px;
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
    font-size: 40px;
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
  align-items: center;
  padding: 150px;
  width: 450px;
`;

function CreateNewEvent(props) {
    return (
      <CreateEventContainer>
        <CreateEventText>Please Choose a Playlist</CreateEventText> 
            <ChooseExsistingButton>Personal </ChooseExsistingButton>
            <ChooseDefaultButton>Spotify Default</ChooseDefaultButton>
      </CreateEventContainer>
    )
  }
  
  export default CreateNewEvent; 