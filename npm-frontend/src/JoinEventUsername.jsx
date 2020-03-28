import React from 'react'
import {
  Link
} from "react-router-dom";
import styled from 'styled-components';
import {RightArrowCircle} from 'styled-icons/boxicons-regular/RightArrowCircle';

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

const JoinEventInput = styled.input`
  width: 150px;
  height: 62px;
  font-size: 45px;
  padding: 0 9px;
  text-align: center;
`;

const JoinEventUsernameButton = styled.button`
  background-color: #65D26E;
  color: white;
  height: 110px;
  width: 200px;
  font-size: 35px;
  border-radius: 13px;
  cursor: pointer;
  margin: 35px;
  border-color: #272727;
  font-weight: bold;
  box-shadow: inset 0px 0px 30px #000000, 13px 12px 20px -5px black;
  transition: width .5s, height .5s, font-size .5s, background-color .5s;

  &:hover {
    height: 130px;
    width: 230px;
    font-size: 40px;
    background-color: #67d667;;
  }

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
`;

const JoinEventForm = styled.form`
  display: flex;
  align-items: center;
  flex-direction: column;
  margin: 60px 0;
`;

const Arrow = styled(RightArrowCircle)`
  height: 85px;
  transition: height .5s;

  ${JoinEventUsernameButton}:hover & {
    height: 94px;
  }
`;

function JoinEventUsername(props) {
  return (
    <JoinEventContainer>
      <JoinEventText>Enter A Username</JoinEventText>
      <JoinEventForm>
        <JoinEventInput style={{width: "300px"}} type="text" placeholder="Username"/>
        <Link to='/guest' style={{ textDecoration: 'none' }}>
          <JoinEventUsernameButton>
            <div>To The Music</div> 
            <Arrow />
          </JoinEventUsernameButton>
        </Link>
      </JoinEventForm>
    </JoinEventContainer>
  )
}

export default JoinEventUsername; 