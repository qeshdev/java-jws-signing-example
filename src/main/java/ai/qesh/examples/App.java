package ai.qesh.examples;

import org.json.JSONObject;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.lang.JoseException;

class App {

  // Ignoring the exception handling for simplicity
  public static void main(String[] args) throws JoseException {
    // Generating one as an example, but please use your own key instead
    final RsaJsonWebKey keyPair = RsaJwkGenerator.generateJwk(2048);

    // Create your JSON payload
    JSONObject metadata = new JSONObject();
    metadata.put("externalReference", "123");

    JSONObject body = new JSONObject();

    body.put("amount", (float) 12.25);
    body.put("expiration", (int) 60);
    body.put("metadata", metadata);

    // Create JWS
    JsonWebSignature jwsBuilder = new JsonWebSignature();
    jwsBuilder.setPayload(body.toString());

    jwsBuilder.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
    jwsBuilder.setKey(keyPair.getPrivateKey());

    // Sign and return the JWS String
    String jws_signature = jwsBuilder.getCompactSerialization();

    // Append the JWS to the request body
    body.put("jws_signature", jws_signature);

    // TODO: Send the request
    System.out.printf(keyPair.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
    System.out.printf(body.toString());
  }
}