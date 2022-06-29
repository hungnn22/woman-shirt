import React from 'react'
import HashLoader from 'react-spinners/HashLoader'

const HashSpinner = () => {
  return (
      <HashLoader
        color={`#7b92db`}
        loading={true}
        size={64}
      style={{
        textAlign: 'center',
        display: 'block',
        justifyContent: 'center',
        alignItems: 'center',
        width: '80%',
        height: '80vh'
      }}
      ></HashLoader>
  )
}

export default HashSpinner