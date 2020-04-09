import React from 'react'
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Logo from './Logo'

const ReturnToJoinCode = styled.button`
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

const CodeErrorContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 70px;
  width: 450px;
`;

const CodeErrorText = styled.div`
  color: white;
  font-size: 25px;
  text-align: center;
  margin: 30px;
`;

function JoinCodeError(props) {
    return (
      <CodeErrorContainer>
        <Logo/>
        <CodeErrorText> The event code entered does not match any codes in our records. Please try again.
 </CodeErrorText>
          <Link to="/join-code">
            <ReturnToJoinCode> Try Again </ReturnToJoinCode>
          </Link>
      </CodeErrorContainer>
    )
  }
  
  export default JoinCodeError; 