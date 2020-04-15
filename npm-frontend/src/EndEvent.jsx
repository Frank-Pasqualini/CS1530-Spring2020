import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Logo from './Logo'

const EndEventButton = styled.button`
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
  box-shadow: inset 0px 0px 12px #000000, 13px 7px 20px -5px black;
  transition: width .5s, height .5s, font-size .5s, background-color .5s;

  &:hover {
    height: 105px;
    width: 220px;
    font-size: 29px;
    background-color: #EBEBF1;
  }
`;

const ErrorContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 70px;
  width: 450px;
`;

const ErrorText = styled.div`
  color: white;
  font-size: 67px;
  text-align: center;
  margin: 45px;
  width: 500px;
`;

const ContinueEvent = styled.button`
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

class EndEvent extends Component {
  render() {
    return (
      <ErrorContainer>
        <Logo/>
        <ErrorText> End Your Event?</ErrorText>
        <Link to="/host">
          <ContinueEvent> Return to Event </ContinueEvent>
        </Link>
        <Link onClick={this.props.endEvent} to="/">
          <EndEventButton> End Event </EndEventButton>
        </Link>
      </ErrorContainer>
    )
  }
}
  
  export default EndEvent; 