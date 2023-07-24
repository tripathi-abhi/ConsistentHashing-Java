package com.Hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class HashService implements IHash {

    private MessageDigest digestInstance;

    HashService(String hashType){
        try {
            setDigestInstance(MessageDigest.getInstance(hashType));
        }
        catch(NoSuchAlgorithmException ex){
            System.out.println("Invalid hash type : "+hashType);
            ex.printStackTrace();
        }
    }

    public void setDigestInstance(MessageDigest digestInstance) {
        this.digestInstance = digestInstance;
    }

    @Override
    public long doHash(String hashKey) {
        if(Objects.isNull(digestInstance))
            throw new IllegalStateException("Digest instance is not instantiated!");

        digestInstance.reset();
        byte[] digest = digestInstance.digest(hashKey.getBytes());

        long hashValue = 0;
        for(int i=0; i<4; i++){
            hashValue <<= 8;
            hashValue |= ((int)digest[i]) & 0xFF;
        }
        return hashValue;
    }
}
