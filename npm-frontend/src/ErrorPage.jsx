import React from 'react'
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Logo from './Logo'

const ReturnHomeButton = styled.button`
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

const ErrorContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 150px;
  width: 450px;
`;

const ErrorText = styled.div`
  color: white;
  font-size: 25px;
  text-align: center;
  margin: 20px;
`;

function ErrorPage(props) {
    return (
      <ErrorContainer>
        <Logo/>
        <ErrorText> A Premium Spotify account is needed to use the features of JamBox. We apologize for any
inconveniences.
 </ErrorText>
          <Link to="/">
            <ReturnHomeButton> Return to Home </ReturnHomeButton>
          </Link>
      </ErrorContainer>
    )
  }
  
  export default ErrorPage; 