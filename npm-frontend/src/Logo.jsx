import React from 'react'
import styled from 'styled-components';

const LogoJ = styled.div`
  color: #2A77C9;
  font-size: ${props => (props.navBar ? "63px" : "250px")};
  margin: ${props => (props.navBar ? "-2px 0px -11px 0px" : "-35px")};  
`;

const LogoTitle = styled.div`
  color: #EBEBEB;
  font-size: ${props => (props.navBar ? "22px" : "50px")};
  font-weight: bold;
`;

const LogoContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: ${props => props.navBar ? "0 0 0 10px" : ""};
`;

function Logo(props) {
  return (
    <LogoContainer {...props}>
      <LogoJ {...props}>J</LogoJ> 
      <LogoTitle {...props}>JAMBOX</LogoTitle>
    </LogoContainer>
  )
}

export default Logo; 