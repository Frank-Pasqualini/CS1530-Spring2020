import React from 'react'
import { Link } from 'react-router-dom';
import styled from 'styled-components';

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

const JoinEventForm = styled.form`
  display: flex;
  align-items: center;
  flex-direction: column;
  margin: 60px 0;
`;

const JoinEventInput = styled.input`
  width: 150px;
  height: 62px;
  font-size: 45px;
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

function JoinEventCode(props) {
  return (
    <JoinEventContainer>
      <JoinEventText>Please Enter Your Event Code</JoinEventText>
      <JoinEventForm>
        <JoinEventInput type="text" placeholder="Code"/>
        <Link to="/join-username">
          <JoinEventCodeButton>Join Event</JoinEventCodeButton>
        </Link>
      </JoinEventForm>
    </JoinEventContainer>
  )
}

export default JoinEventCode; 