package com.whitrus.spring.tester.domain.post;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whitrus.spring.tester.domain.post.model.DoiDTO;
import com.whitrus.spring.tester.domain.post.model.PostInsertDTO;
import com.whitrus.spring.tester.domain.post.model.PostUpdateDTO;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping
	public ResponseEntity<?> findAllPosts(@RequestParam(defaultValue = "basic") String view, Pageable pageable) {

		if (view.equals("details")) {
			return ResponseEntity.ok(postService.findAllPostsWithDetailsAsDTO(pageable));
		}

		return ResponseEntity.ok(postService.findAllPostsAsDTO(pageable));
	}

	@GetMapping("/{postId}")
	public ResponseEntity<?> findPostById(@PathVariable Long postId, @RequestParam(defaultValue = "basic") String view) {

		if (view.equals("details")) {
			return ResponseEntity.ok(postService.findPostWithDetailsByIdAsDTO(postId));
		}

		return ResponseEntity.ok(postService.findPostByIdAsDTO(postId));
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> deletePostById(@PathVariable Long postId) {

		postService.deletePostById(postId);
		
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/{postId}")
	public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostUpdateDTO postDTO, @RequestParam(defaultValue = "basic") String view) {
		
		postService.updatePost(postId, postDTO);
		
		return findPostById(postId, view);
	}
	
	@PostMapping
	public ResponseEntity<?> createNewPost(@RequestBody PostInsertDTO postDTO, @RequestParam(defaultValue = "basic") String view) {

		return createPostResponse(postService.createNewPost(postDTO), view);
	}
	
	@PostMapping("/random")
	public ResponseEntity<?> createRandomPost(@RequestParam(defaultValue = "basic") String view) {

		return createPostResponse(postService.createRandomPost(), view);
	}
	
	@PostMapping("/file")
	public ResponseEntity<?> uploadFile(@RequestPart @Valid PostInsertDTO post, @RequestPart MultipartFile file, @RequestParam(defaultValue = "basic") String view) throws FileNotFoundException, IOException{
		
		File userHome = new File(System.getProperty("user.home"));
		File userDesktop = new File(userHome, "desktop");
		File outputFile = new File(userDesktop, file.getOriginalFilename());

		file.transferTo(outputFile);

		return createNewPost(post, view);
	}
	
	@PostMapping("/doi")
	public ResponseEntity<?> testDOI(@Valid @RequestBody DoiDTO doiDTO, @RequestParam(defaultValue = "basic") String view) {

		return createPostResponse(postService.createRandomPost(), view);
	}
	
	private ResponseEntity<?> createPostResponse(Long postId, String view) {
		
		Object post = findPostById(postId, view).getBody();		
		URI uri = linkTo(methodOn(PostController.class).findPostById(postId, null)).toUri();
		
		return ResponseEntity.created(uri).body(post);
	}
}
