package hieu.nv.bytela.service;

import hieu.nv.bytela.model.entity.shortlink.ShortLink;
import hieu.nv.bytela.model.entity.shortlink.ShortLinkRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class ShortLinkService {
    private final ShortLinkRepository repository;

    @Value("${config.shortLinkSize}")
    private int shortLinkSize;

    public ShortLinkService(ShortLinkRepository repository) {
        this.repository = repository;
    }

    public ShortLink storeShortLink(String link) throws NoSuchAlgorithmException {
        String shortedLink = this.hashLink(link);
        return this.repository.save(new ShortLink(shortedLink, link));
    }

    private String hashLink(String link) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        return Base64.getEncoder().encodeToString(messageDigest.digest(link.getBytes(StandardCharsets.UTF_8))).substring(0, shortLinkSize);
    }

    @Cacheable(key = "#shortLink", value = "links")
    public Optional<ShortLink> getShortLink(String shortLink)
    {
        return this.repository.findById(shortLink);
    }

    public Optional<ShortLink> storeCustomizedShortLink(ShortLink shortLink)
    {
        if (shortLink.getDestinationLink() == null ||  shortLink.getDestinationLink().isBlank() || shortLink.getId() == null || shortLink.getId().isBlank())
        {
            return Optional.empty();
        }

        if (repository.existsById(shortLink.getId()))
            return Optional.empty();
        return Optional.of(repository.save(shortLink));

    }

}
